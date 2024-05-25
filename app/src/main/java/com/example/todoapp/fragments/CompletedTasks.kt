package com.example.todoapp.fragments


import android.content.ContentValues
import android.content.ContentValues.TAG
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

        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)
        taskAdapter.setOnTaskItemClickListener(object : TaskAdapter.OnTaskItemClickListener {

            override fun onTaskItemClicked(taskModel: TasksModel) {
                Toast.makeText(
                    requireContext(),
                    "Clicked on task: ${taskModel.getTaskName()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding?.recyclerTasksCompleted?.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter=taskAdapter
        }
        val taskImageResource = R.drawable.tasks_done
        val taskStatusImageResource = R.drawable.tick

//        taskRecyclerCompleted = binding?.recyclerTasksCompleted!!
        db = Firebase.firestore
        auth = Firebase.auth
        val resTaskImage = resources.getIdentifier(
            "tasks_done",
            "drawable",
            this.taskBinding.root.context.packageName
        )

        val docRef = db.collection("addTask")
        docRef
            .whereEqualTo("taskStatus", "Active")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                taskModelArrayList.clear()

                if (snapshot != null) {
                    for (document in snapshot) {
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
                        taskModelArrayList.add(task)
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    }
                    taskAdapter.notifyDataSetChanged()
//                    taskRecyclerCompleted.adapter = taskAdapter
//                    val linearLayoutManager =
//                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//
//                    taskRecyclerCompleted.layoutManager = linearLayoutManager
//                    taskRecyclerCompleted.adapter = taskAdapter

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

        return binding?.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}