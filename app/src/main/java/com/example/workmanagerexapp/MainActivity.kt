package com.example.workmanagerexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //subclass of our WorkRequest
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SendNotificationWorker>().build()


        btnStartEnqueueWork.setOnClickListener {
            //Enqueuing the work request
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
        }


        // listening the work status
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, Observer {  workInfo ->

                //Displaying the status
                if (workInfo != null ) {
                    tvWorkStatus.append(workInfo.state.name + "\n");
                }
            })

    }
}