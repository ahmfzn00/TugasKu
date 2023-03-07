package com.codenesia.tugasku.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(job: Job)

    @Update
    fun update(job: Job)

    @Delete
    fun delete(job: Job)

    @Query("SELECT * FROM job ORDER BY id ASC")
    fun getAllJobs(): LiveData<List<Job>>
}