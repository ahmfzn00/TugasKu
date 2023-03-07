package com.codenesia.tugasku.ui.insert

import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.codenesia.tugasku.R
import com.codenesia.tugasku.database.Job
import com.codenesia.tugasku.databinding.ActivityJobUpdateBinding
import com.codenesia.tugasku.helper.DateHelper
import com.codenesia.tugasku.helper.ViewModelFactory
import com.codenesia.tugasku.ui.main.MainActivity

class JobUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_JOB = "extra_job"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var job: Job? = null

    private lateinit var jobAddUpdateViewModel: JobAddUpdateViewModel

    private var _binding : ActivityJobUpdateBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityJobUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        jobAddUpdateViewModel = obtainViewModel(this@JobUpdateActivity)

        job = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(EXTRA_JOB, Job::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_JOB)
        }
        if (job != null) {
            isEdit = true
        } else {
            job = Job()
        }

        var actionBarTitle : String
        var btnTitle : String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            if (job != null) {
                job?.let { job ->
                    binding?.edtTitle?.setText(job.title)
                    binding?.edtDescription?.setText(job.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()

            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }

                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }

                else -> {
                    job.let { job ->
                        job?.title = title
                        job?.description = description
                    }

                    if (isEdit) {
                        jobAddUpdateViewModel.update(job as Job)
                        showToast(getString(R.string.changed))
                    } else {
                        job.let { job ->
                            job?.date = DateHelper.getCurrentDate()
                        }
                        jobAddUpdateViewModel.insert(job as Job)
                        showToast(getString(R.string.added))
                    }

                    finish()
                }

            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): JobAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[JobAddUpdateViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_file, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle : String
        val dialogMessage : String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    jobAddUpdateViewModel.delete(job as Job)
                    showToast(getString(R.string.deleted))
                }

                finish()
            }

            setNegativeButton(getString(R.string.no)) {dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}