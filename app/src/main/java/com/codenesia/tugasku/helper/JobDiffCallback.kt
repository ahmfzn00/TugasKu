package com.codenesia.tugasku.helper

import androidx.recyclerview.widget.DiffUtil
import com.codenesia.tugasku.database.Job

class JobDiffCallback(private val mOldJobList: List<Job>, private val mNewJobList: List<Job>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldJobList.size
    }

    override fun getNewListSize(): Int {
        return mNewJobList.size    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldJobList[oldItemPosition].id == mNewJobList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldJobList[oldItemPosition]
        val newEmployee = mNewJobList[newItemPosition]

        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
    }

}