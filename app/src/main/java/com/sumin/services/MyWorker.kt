package com.sumin.services

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE_KEY, 0)
        for (i in 0 until 3) {
            Thread.sleep(1_000L)
            log("Page: $page Timer: $i")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("MyWorker_TAG", "Message: $message")
    }

    companion object {
        private const val PAGE_KEY = "page_key"
        const val WORKER_NAME = "worker_name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .apply {
                    setInputData(workDataOf(PAGE_KEY to page))
                        .setConstraints(makeConstrains())
                }
                .build()
        }

        private fun makeConstrains() = Constraints
            .Builder()
            .setRequiresCharging(true)
            .build()

    }
}