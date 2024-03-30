package com.example.todoapp.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.todoapp.R
import com.example.todoapp.adapters.PageAdapter
import com.example.todoapp.databinding.ActivityUpdateTasksBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class UpdateTasks : AppCompatActivity(), View.OnClickListener {
    private lateinit var def: ColorStateList
    private lateinit var item1: TextView
    private lateinit var item2: TextView
    private lateinit var select: TextView
    private lateinit var addTaskBtn: ImageView
    private lateinit var dialogLayout: View
    private lateinit var viewPager: ViewPager
    private lateinit var taskNameValue: String
    private lateinit var taskStatusValue: String
    private lateinit var taskDurationValue: String
    private lateinit var taskDescriptionValue: String


    private lateinit var binding: ActivityUpdateTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item1 = binding.include.completed
        item2 = binding.include.notCompleted
        item1.setOnClickListener(this)
        item2.setOnClickListener(this)
        select = binding.include.select
        def = item2.textColors

        viewPager = binding.viewPager
        addTaskBtn = binding.addTaskBtn
        val inflater = layoutInflater
        dialogLayout = inflater.inflate(R.layout.dialog_layout, null)
        binding.addTaskBtn.setOnClickListener {
            addTaskDialog(it)
        }

        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        select.animate().x(0f).duration = 100
                        item1.setTextColor(Color.WHITE)
                        item2.setTextColor(def)
                    }

                    1 -> {
                        item1.setTextColor(def)
                        item2.setTextColor(Color.WHITE)
                        val size = item2.width
                        select.animate().x(size.toFloat()).duration = 100

                    }

                }

            }


            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.completed -> {
                select.animate().x(0f).duration = 100
                item1.setTextColor(Color.WHITE)
                item2.setTextColor(def)
                if (viewPager.currentItem != 0) {
                    viewPager.setCurrentItem(viewPager.currentItem - 1, true);
                    Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.notCompleted -> {
                item1.setTextColor(def)
                item2.setTextColor(Color.WHITE)
                val size = item2.width
                select.animate().x(size.toFloat()).duration = 100
                if (viewPager.currentItem != 1) {
                    viewPager.setCurrentItem(viewPager.currentItem + 1, true);
                    Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun addTaskDialog(view: View) {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add Task")
        builder.setCancelable(false)
        dialogLayout = inflater.inflate(R.layout.dialog_layout, null)

        val taskName = dialogLayout.findViewById<EditText>(R.id.taskName)
        val taskStatus = dialogLayout.findViewById<AutoCompleteTextView>(R.id.taskStatus)
        val taskDurationTv = dialogLayout.findViewById<TextView>(R.id.taskDurationTv)
        val taskDescription = dialogLayout.findViewById<EditText>(R.id.taskDescription)

        builder.setView(dialogLayout)


        taskDurationTv.setOnClickListener {

            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select the Time to Complete the Task")
                    .build()

            picker.show(supportFragmentManager, "timePicker")
            picker.addOnPositiveButtonClickListener {
                val hour = if (picker.hour > 12) picker.hour - 12 else picker.hour
                val amPm = if (picker.hour >= 12) "PM" else "AM"
                taskDurationValue = "Complete till :${hour} ${picker.minute} $amPm"
                taskDurationTv.text = taskDurationValue
            }
            picker.addOnNegativeButtonClickListener {
            }

        }
        builder.setView(dialogLayout)
        val statusArray = resources.getStringArray(R.array.status)


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, statusArray
        )
        taskStatus.setAdapter(adapter)

        builder.setPositiveButton("Add Task") { _, _ ->
            taskNameValue = taskName.text.toString()
            taskStatusValue = taskStatus.text.toString()
            taskDescriptionValue = taskDescription.text.toString()

            Toast.makeText(
                this,
                "Task Name is $taskNameValue Task task Status is $taskStatusValue ",
                Toast.LENGTH_LONG
            ).show()
            Toast.makeText(
                this,
                "Task Duration is $taskDurationValue Task Description is $taskDescriptionValue ",
                Toast.LENGTH_LONG
            ).show()

        }
        builder.setNegativeButton("Cancel") { _, _ ->

        }
        builder.show()
    }

}

