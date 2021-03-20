package com.example.workmanagerexapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SendNotificationWorker(context: Context,  workParams: WorkerParameters) : Worker(context, workParams) {

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}