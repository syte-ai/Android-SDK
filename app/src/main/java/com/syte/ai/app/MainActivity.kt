package com.syte.ai.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.syte.ai.R
import com.syte.ai.Syte.Companion.getBoundsForImage
import com.syte.ai.Syte.Companion.getOffers
import com.syte.ai.Syte.Companion.modifyConfig
import com.syte.ai.Syte.Companion.registerCallback
import com.syte.ai.data.ImageBounds
import com.syte.ai.data.SyteResponse
import com.syte.ai.data.SyteResponse.Companion.NETWORK_UNAVAILABLE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_TO_ACCESS_GALLERY = 100
        const val REQUEST_OPEN_GALLERY = 101
    }

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView.layoutManager = LinearLayoutManager(this)

        registerCallback {
            onSyteReady()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_OPEN_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    val uri = data?.data
                    currentImageUri = uri

                    if (uri != null) {
                        Glide.with(this)
                            .load(uri)
                            .into(mImageView)
                        mImageView.visibility = VISIBLE
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_TO_ACCESS_GALLERY -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openGallery()
            else
                Toast.makeText(this, "Permission denied to access the gallery.", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(gallery, "Select picture"),
            REQUEST_OPEN_GALLERY
        )
    }

    private fun onSyteReady() {
        modifyConfig(currency = "EUR")

        mChooseImgButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_TO_ACCESS_GALLERY
                )
            } else {
                openGallery()
            }
        }

        mGetBoundsButton.setOnClickListener {
            val url = imageUrlField.text.toString()
            if (url.isEmpty()) {
                Toast.makeText(this, "Please specify an image URL.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            beforeRequest(mGetBoundsButton)
            getBoundsForImage(url) {
                processResponse(it)
                afterRequest(mGetBoundsButton)
            }
        }

        mGetBinaryBoundsButton.setOnClickListener {
            val uri = currentImageUri
            if (uri == null) {
                Toast.makeText(this, "Please choose an image from your gallery.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            beforeRequest(mGetBinaryBoundsButton)
            getBoundsForImage(uri) {
                processResponse(it)
                afterRequest(mGetBinaryBoundsButton)
            }
        }
    }

    private fun processResponse(it: SyteResponse<ImageBounds>) {
        if (it.status == 503 && it.statusDescription == NETWORK_UNAVAILABLE)
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show()

        var boundsList = mutableListOf<BoundItem>()
        val bounds = it.data
        if (bounds != null)
            boundsList = bounds.bounds.map { BoundItem(it) }.toMutableList()

        if (boundsList.isEmpty())
            Toast.makeText(this, "No bounds found.", Toast.LENGTH_LONG).show()

        val adapter = BoundsAdapter(boundsList)
        adapter.onBoundSelected = { boundItem, position, button ->
            val bound = boundItem.bound

            button.text = "Loading..."
            button.isEnabled = false
            getOffers(bound.offerUrl) {
                boundItem.offersCount = it.data?.ads?.size
                adapter.notifyItemChanged(position)
                button.text = "Get offers"
                button.isEnabled = true
            }
        }
        mRecyclerView.adapter = adapter
    }

    private fun beforeRequest(button: Button) {
        mRecyclerView.visibility = GONE
        mProgressBar.visibility = VISIBLE
        button.isEnabled = false
    }

    private fun afterRequest(button: Button) {
        mRecyclerView.visibility = VISIBLE
        mProgressBar.visibility = GONE
        button.isEnabled = true
    }
}
