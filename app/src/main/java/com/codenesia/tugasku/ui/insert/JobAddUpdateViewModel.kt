package com.codenesia.tugasku.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.codenesia.tugasku.database.Job
import com.codenesia.tugasku.repository.JobRepository

class JobAddUpdateViewModel(application: Application) : ViewModel() {
    private val mJobRepository: JobRepository = JobRepository(application)

    fun insert(job: Job) {
        mJobRepository.insert(job)
    }
    fun update(job: Job) {
        mJobRepository.update(job)
    }
    fun delete(job: Job) {
        mJobRepository.delete(job)
    }
}