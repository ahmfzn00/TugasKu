package com.codenesia.tugasku.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codenesia.tugasku.database.Job
import com.codenesia.tugasku.repository.JobRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mJobRepository: JobRepository = JobRepository(application)

    fun getAllJobs(): LiveData<List<Job>> = mJobRepository.getAllJobs()
}