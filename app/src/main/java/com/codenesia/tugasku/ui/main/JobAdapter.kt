package com.codenesia.tugasku.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codenesia.tugasku.database.Job
import com.codenesia.tugasku.databinding.ItemJobBinding
import com.codenesia.tugasku.helper.JobDiffCallback
import com.codenesia.tugasku.ui.insert.JobUpdateActivity

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private  val listJobs = ArrayList<Job>()

    fun setListJobs(listJobs : List<Job>) {
        val diffCallback = JobDiffCallback(this.listJobs, listJobs)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listJobs.clear()
        this.listJobs.addAll(listJobs)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(listJobs[position])
    }

    override fun getItemCount(): Int {
        return listJobs.size
    }

    inner class JobViewHolder(private val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job :Job) {
            with(binding) {
                tvItemTitle.text = job.title
                tvItemDate.text = job.date
                tvItemDescription.text = job.description

                cvItemNote.setOnClickListener{
                    val intent = Intent(it.context, JobUpdateActivity::class.java)
                    intent.putExtra(JobUpdateActivity.EXTRA_JOB, job)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}