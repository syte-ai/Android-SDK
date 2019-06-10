package com.syte.ai.app

import android.animation.LayoutTransition.CHANGING
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.syte.ai.R
import kotlinx.android.synthetic.main.single_bound_layout.view.*

/**
 * Created by Syte on 4/9/2019.
 */
class BoundsAdapter(private var mBoundsList: MutableList<BoundItem>) :
    RecyclerView.Adapter<BoundsAdapter.ViewHolder>() {

    var onBoundSelected: ((boundItem: BoundItem, position: Int, button: Button) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_bound_layout, parent, false)
        )

    override fun getItemCount() = mBoundsList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mBoundsList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvBoundName: TextView = itemView.tvBoundName
        var tvBoundOffers: TextView = itemView.tvBoundOffers
        var boundLayout: ConstraintLayout = itemView.boundLayout
        var getOffersButton: Button = itemView.getOffersButton

        init {
            boundLayout.layoutTransition.enableTransitionType(CHANGING)
            getOffersButton.setOnClickListener {
                onBoundSelected?.invoke(mBoundsList[adapterPosition], adapterPosition, getOffersButton)
            }
        }

        fun bind(bound: BoundItem) {
            tvBoundName.text = "Bound ${adapterPosition + 1}"
            val offersCount = bound.offersCount
            if (offersCount != null) {
                tvBoundOffers.visibility = VISIBLE
                tvBoundOffers.text = "$offersCount ads found."
            } else
                tvBoundOffers.visibility = GONE
        }
    }
}