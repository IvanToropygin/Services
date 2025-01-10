package com.sumin.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent")
        val page = intent.getIntExtra(PAGE_KEY, 0)

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
        Log.d("MyJobIntentService_TAG", "Message: $message")
    }

    companion object {
        private const val PAGE_KEY = "page_key"
        private const val JOB_ID = 123

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int) =
            Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE_KEY, page)
            }
    }
}