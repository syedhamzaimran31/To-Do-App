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
    private lateinit var taskDurationTv: TextView
    private lateinit var dialogLayout: View

    private lateinit var binding: ActivityUpdateTasksBinding
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val toolbar: androidx.appcompat.widget.Toolbar? = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
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

        taskDurationTv = dialogLayout.findViewById<TextView>(R.id.taskDurationTv)
        taskDurationTv.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .build()
            Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
        }


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
        val taskStatus = dialogLayout.findViewById<AutoCompleteTextView>(R.id.statusAutoTv)
//        val taskDurationDialog = dialogLayout.findViewById<TimePicker>(R.id.taskDurationDialog)
        val taskDurationTv = dialogLayout.findViewById<TextView>(R.id.taskDurationTv)

        taskDuration(taskDurationTv)
        builder.setView(dialogLayout)
        val statusArray = resources.getStringArray(R.array.status)


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, statusArray
        )
        taskStatus.setAdapter(adapter)

        builder.setPositiveButton("Add Task") { _, _ ->
            Toast.makeText(
                applicationContext,
                "EditText is " + taskName.text.toString(),
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(
                applicationContext,
                "EditText is " + taskName.text.toString(),
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.show()
    }

    private fun taskDuration(taskTv: TextView) {
        taskTv.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .build()
            Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
        }
    }

}

