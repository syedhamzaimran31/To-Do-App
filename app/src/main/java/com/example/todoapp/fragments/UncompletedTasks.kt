package com.example.todoapp.fragments

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.databinding.FragmentUncompletedTasksBinding
import com.example.todoapp.model.TasksModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class UncompletedTasks : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var taskRecyclerIncomplete: RecyclerView


    private var binding: FragmentUncompletedTasksBinding? = null
    private val taskBinding get() = binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUncompletedTasksBinding.inflate(inflater,container,false)

        val taskImageResource = R.drawable.tasks_pending
        val taskStatusImageResource = R.drawable.cross
        taskRecyclerIncomplete = binding?.recyclerTasksIncomplete!!

        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        db = Firebase.firestore
        auth = Firebase.auth

        val docRef = db.collection("addTask")
        docRef
            .whereEqualTo("taskStatus", "INCOMPLETE")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val task = document.toObject(TasksModel::class.java)
                    val taskDate = task.getCreatedAt()
                    val parts = taskDate.split(", ")
                    val timestamp = parts[0].toLong()
                    val instant = Instant.ofEpochMilli(timestamp)
                    val formattedDateTime =
                        LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    task.setCreatedAt(formattedDateTime)
                    task.setTaskImage(taskImageResource)
                    task.setTaskStatusImage(taskStatusImageResource)
                    Toast.makeText(requireContext(), formattedDateTime, Toast.LENGTH_SHORT).show()
                    taskModelArrayList.add(task)
                    Toast.makeText(requireContext(), "$document", Toast.LENGTH_LONG).show()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)
                taskRecyclerIncomplete.adapter = taskAdapter
                val linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                taskRecyclerIncomplete.layoutManager = linearLayoutManager
                taskRecyclerIncomplete.adapter = taskAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

//        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
//        taskModelArrayList.add(
//            TasksModel("DSA in Java", "Status: Active", "Date: 23-03-24", taskImageResource, taskStatusImageResource)
//        )
//        taskModelArrayList.add(
//            TasksModel("DSA in Java", "Status: Active", "Date: 12-03-24", taskImageResource, taskStatusImageResource)
//        )
//        taskModelArrayList.add(
//            TasksModel("DSA in Java", "Status: Active", "Date: 09-03-24", taskImageResource, taskStatusImageResource)
//        )
//        val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)

//        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

//        taskRecycler?.layoutManager = linearLayoutManager
//        taskRecycler?.adapter = taskAdapter
        return binding?.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}