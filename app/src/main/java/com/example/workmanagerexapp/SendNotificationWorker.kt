package com.example.workmanagerexapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class SendNotificationWorker(context: Context, workParams: WorkerParameters) : Worker(
        context,
        workParams
) {

    companion object {
        const val TASK_DATA = "task_data"
    }

    /*
    * responsible for doing work so we put the work here that is need to be performed.
    * to display a notification i am calling displayNotification method
     */
    override fun doWork(): Result {
        //getting the input data
        val taskData = inputData.getString(TASK_DATA)

        if (taskData != null) {
            displayNotification("WorkManager", taskData)
        }

        //setting output data
        val data: Data = Data.Builder()
                .putString(TASK_DATA, "The conclusion of the task")
                .build()

        return Result.success(data)
    }



    /*
    * generating a simple notification
    */

    private fun displayNotification(title: String, task: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    "workmanagerexample",
                    "workmanagerexample",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, "workmanagerexample")
            .setContentTitle(title)
            .setContentText(task)
            .setSmallIcon(R.mipmap.ic_launcher_round)
        notificationManager.notify(1, notification.build())
    }
}