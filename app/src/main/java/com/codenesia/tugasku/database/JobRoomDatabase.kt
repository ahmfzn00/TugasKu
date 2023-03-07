package com.codenesia.tugasku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Job::class], version = 1)
abstract class JobRoomDatabase : RoomDatabase() {
    abstract fun jobDao() : JobDao

    companion object {
        @Volatile
        private var INSTANCE : JobRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): JobRoomDatabase {
            if (INSTANCE == null) {
                synchronized(JobRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        JobRoomDatabase::class.java, "job_database")
                        .build()
                }
            }

            return INSTANCE as JobRoomDatabase
        }
    }
}