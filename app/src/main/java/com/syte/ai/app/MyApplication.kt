package com.syte.ai.app

import android.app.Application
import com.syte.ai.Syte.Companion.init

/**
 * Created by Syte on 4/8/2019.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init(this, [your_account_id], "[your_signature]")
    }
}