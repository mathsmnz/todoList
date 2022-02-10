package br.com.dio.todolist.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.todolist.databinding.ActivityAddTaskBinding
import br.com.dio.todolist.datasource.TaskDataSource
import br.com.dio.todolist.extensions.format
import br.com.dio.todolist.extensions.text
import br.com.dio.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentListener()
    }

    private fun intentListener(){
        binding.dateInputBox.editText?.setOnClickListener {
            Toast.makeText(this, "Selecionar data", Toast.LENGTH_LONG).show()

            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.dateInputBox.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.hourInputBox.editText?.setOnClickListener {
            Toast.makeText(this, "Selecionar hora", Toast.LENGTH_LONG).show()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                binding.hourInputBox.text = "${hour}:${minute}"
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.createTask.setOnClickListener {
            val task = Task(
                title = binding.titleInputBox.text,
                date = binding.dateInputBox.text,
                hour = binding.hourInputBox.text,
                description = binding.descriptionInputBox.text
            )
            TaskDataSource.insertTask(task)
            Log.d("TAG", "insertListeners: ${TaskDataSource.getList()}")
        }
    }
}