package com.example.workmanagerexapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creating a data object for passing the data with workRequest
        //we can put as many variables needed
        val data = Data.Builder()
                .putString(SendNotificationWorker.TASK_DATA, "The task data passed to send notification worker")
                .build()


        //subclass of our WorkRequest
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SendNotificationWorker>()
                .setInputData(data)
                .build()


        btnStartEnqueueWork.setOnClickListener {
            //Enqueuing the work request
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
        }

        printWorkStatus(oneTimeWorkRequest)
    }


    private fun printWorkStatus(oneTimeWorkRequest: WorkRequest) {
        // listening the work status
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, Observer { workInfo ->

                //Displaying the status
                if (workInfo != null && workInfo.state.isFinished) {
                    //receiving back the data
                    tvWorkStatus.append(workInfo.outputData.getString(SendNotificationWorker.TASK_DATA) + "\n");

                    tvWorkStatus.append(workInfo.state.name + "\n");
                }
            })
    }
}