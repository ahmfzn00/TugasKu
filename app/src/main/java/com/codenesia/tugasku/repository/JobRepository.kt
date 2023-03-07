package com.codenesia.tugasku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.codenesia.tugasku.database.Job
import com.codenesia.tugasku.database.JobDao
import com.codenesia.tugasku.database.JobRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JobRepository(application: Application) {
    private val mJobDao : JobDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = JobRoomDatabase.getDatabase(application)
        mJobDao = db.jobDao()
    }

    fun getAllJobs() : LiveData<List<Job>> = mJobDao.getAllJobs()

    fun insert(job: Job) {
        executorService.execute { mJobDao.insert(job) }
    }

    fun delete(job: Job) {
        executorService.execute { mJobDao.delete(job) }
    }
    fun update(job: Job) {
        executorService.execute { mJobDao.update(job) }
    }

}