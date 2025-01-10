package com.sumin.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        scope.launch {
            var workItem = params?.dequeueWork()
            while (workItem != null) {
                val page = workItem.intent?.getIntExtra(PAGE_KEY, 0)

                for (i in 0 until 3) {
                    delay(1_000L)
                    log("Page:$page Timer: $i")
                }
                params?.completeWork(workItem)
                workItem = params?.dequeueWork()
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    private fun log(message: String) {
        Log.d("My_JobService_TAG", "Message: $message")
    }

    companion object {
        const val COMPONENT_ID = 123
        const val PAGE_KEY = "page_key"

        fun newIntent(pageNum: Int): Intent {
            return Intent().apply { putExtra(PAGE_KEY, pageNum) }
        }
    }
}