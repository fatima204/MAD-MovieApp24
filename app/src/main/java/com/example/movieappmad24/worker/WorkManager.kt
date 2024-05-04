package com.example.movieappmad24.worker

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkManager(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun tellWorker(){
        val workBuilder = OneTimeWorkRequestBuilder<Worker>()
        workManager.enqueue(workBuilder.build())
    }
}