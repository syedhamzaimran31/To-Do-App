package com.example.todoapp.fragments

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.databinding.FragmentCompleteTasksBinding
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

class CompletedTasks : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRecyclerCompleted: RecyclerView
    private var binding: FragmentCompleteTasksBinding? = null
    private val taskBinding get() = binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompleteTasksBinding.inflate(inflater, container, false)

        val taskImageResource = R.drawable.to_do_list
        val taskStatusImageResource = R.drawable.tick
        taskRecyclerCompleted = binding?.recyclerTasksCompleted!!
        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        db = Firebase.firestore
        auth = Firebase.auth

        val docRef = db.collection("addTask")
        docRef
            .whereEqualTo("taskStatus", "COMPLETED")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    val taskName= document.data["taskName"].toString()
//                    val taskStatus= document.data["taskStatus"].toString()
//                    val taskDate= document.data["taskDate"].toString()
                    val task = document.toObject(TasksModel::class.java)
                    val taskDate = task.getCreatedAt()
                    val parts=taskDate.split(", ")
                    val timestamp=parts[0].toLong()
                    val dateTime=parts[1]
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val instant=Instant.ofEpochMilli(timestamp)
                    val dateTimeString=LocalDateTime.parse(dateTime,formatter)
                    val formattedDateTime=dateTimeString.format(formatter)

//                    val formattedTaskDate = taskDate.format(formatter)
                    Toast.makeText(requireContext(), formattedDateTime, Toast.LENGTH_SHORT).show()
                    taskModelArrayList.add(task)
                    Toast.makeText(requireContext(), "$document", Toast.LENGTH_LONG).show()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)
                taskRecyclerCompleted.adapter = taskAdapter
                val linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                taskRecyclerCompleted.layoutManager = linearLayoutManager
                taskRecyclerCompleted.adapter = taskAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        docRef
            .whereEqualTo("taskStatus", "COMPLETED")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Toast.makeText(requireContext(), "$document", Toast.LENGTH_LONG).show()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

//        taskModelArrayList.add(
//            TasksModel(
//                "DSA in Java",
//                "Status: InActive",
//                "Date: 12-01-24",
//                taskImageResource,
//                taskStatusImageResource
//            )
//        )
//        taskModelArrayList.add(
//            TasksModel(
//                "DSA in Java",
//                "Status: InActive",
//                "Date: 19-01-24",
//                taskImageResource,
//                taskStatusImageResource
//            )
//        )
//        taskModelArrayList.add(
//            TasksModel(
//                "DSA in Java",
//                "Status: InActive",
//                "Date: 14-02-24",
//                taskImageResource,
//                taskStatusImageResource
//            )
//        )
//        taskModelArrayList.add(
//            TasksModel(
//                "DSA in Java",
//                "Status: InActive",
//                "Date: 15-02-24",
//                taskImageResource,
//                taskStatusImageResource
//            )
//        )


//        val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)


        return binding?.root

    }

//    fun getRecyclerView(): RecyclerView {
//        return taskRecyclerCompleted
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}