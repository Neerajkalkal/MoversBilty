package com.example.gangapackagesolution

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val message = "Hello from Spring Boot!"
        val intent = Intent("com.example.NOTIFY").apply {
            putExtra("message", message)
        }
        applicationContext.sendBroadcast(intent)
        return Result.success()
    }
}