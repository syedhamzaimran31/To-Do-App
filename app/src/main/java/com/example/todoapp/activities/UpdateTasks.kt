package com.example.todoapp.activities

import android.content.ContentValues
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.todoapp.R
import com.example.todoapp.adapters.PageAdapter
import com.example.todoapp.databinding.ActivityUpdateTasksBinding
import com.example.todoapp.utils.TaskStatus
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


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
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    lateinit var userId: String
    private lateinit var documentId: String

    private lateinit var binding: ActivityUpdateTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        auth = Firebase.auth
        documentId = UUID.randomUUID().toString()

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addTaskDialog(it)
            }
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addTaskDialog(view: View) {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add Task")
        builder.setCancelable(false)
        dialogLayout = inflater.inflate(R.layout.dialog_layout, null)

        builder.setPositiveButton("Add Task", null)
        builder.setView(dialogLayout)

        val taskName = dialogLayout.findViewById<EditText>(R.id.taskName)
        val taskStatus = dialogLayout.findViewById<AutoCompleteTextView>(R.id.taskStatus)
        val taskDurationTv = dialogLayout.findViewById<TextView>(R.id.taskDurationTv)
        val taskDescription = dialogLayout.findViewById<EditText>(R.id.taskDescription)
        val taskImage = dialogLayout.findViewById<ImageView>(R.id.taskImageValue)

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
                taskDurationValue = "$hour ${picker.minute} $amPm"
                taskDurationTv.text = taskDurationValue
            }
            picker.addOnNegativeButtonClickListener {
            }

        }
        val statusArray = resources.getStringArray(R.array.status)


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, statusArray
        )
        taskStatus.setAdapter(adapter)

        builder.setPositiveButton("Add Task") setOnClickListener@{ _, _ ->
            taskNameValue = taskName.text.toString()
            taskStatusValue = taskStatus.text.toString()
            taskDescriptionValue = taskDescription.text.toString()

            Toast.makeText(
                this,
                "Task Name is $taskNameValue Task task Status is $taskStatusValue ",
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(
                this,
                "Task Duration is $taskDurationValue Task Description is $taskDescriptionValue ",
                Toast.LENGTH_SHORT
            ).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveDataInFireStore()
                Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show()
            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }
        val dialog = builder.create()
        dialog.show();

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

        //add Text Watcher to check if the all the fields of Dialog are not empty
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val checkFields =
                    !taskName.text.isNullOrBlank() && !taskStatus.text.isNullOrBlank() &&
                            !taskDurationTv.text.isNullOrBlank() && !taskDescription.text.isNullOrBlank()
                positiveButton.isEnabled = checkFields
            }

        }

        taskName.addTextChangedListener(textWatcher)
        taskStatus.addTextChangedListener(textWatcher)
        taskDurationTv.addTextChangedListener(textWatcher)
        taskDescription.addTextChangedListener(textWatcher)

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false;
        dialog.show()
    }

    fun showTaskDialog(){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add Task")
        builder.setCancelable(false)
        dialogLayout = inflater.inflate(R.layout.dialog_layout, null)

        builder.setPositiveButton("Add Task", null)

        builder.setView(dialogLayout)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveDataInFireStore() {

        userId = Firebase.auth.currentUser?.uid.toString()

        val createdTime = System.currentTimeMillis()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        Toast.makeText(applicationContext, taskStatusValue, Toast.LENGTH_SHORT).show()
        val addTask = hashMapOf(
            "id" to documentId,
            "userId" to userId,
            "taskName" to taskNameValue,
            "taskDuration" to taskDurationValue,
            "taskStatus" to if (taskStatusValue == "Active") TaskStatus.Active else {
                TaskStatus.InActive
            },
            "taskDescription" to taskDescriptionValue,
            "taskImage" to 0,
            "taskStatusImage" to 0,
            "createdAt" to "$createdTime, $current",

            )

        db.collection("addTask").document(documentId)
            .set(addTask)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Task added with ID: $documentId")
                Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding Task")
                Toast.makeText(this, "Task Failed", Toast.LENGTH_LONG).show()

            }
    }

}
