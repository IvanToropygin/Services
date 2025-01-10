package com.sumin.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentService2 : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE_KEY, 0) ?: 0

        for (i in 0 until 3) {
            Thread.sleep(1_000L)
            log("Page: $page Timer: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyIntentService2_TAG", "Message: $message")
    }

    companion object {
        private const val NAME = "MyIntentService2"
        private const val PAGE_KEY = "page_key"

        fun newIntent(context: Context, page: Int) =
            Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE_KEY, page)
            }
    }
}