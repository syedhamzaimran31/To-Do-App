package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.databinding.FragmentCompleteTasksBinding
import com.example.todoapp.model.TasksModel

class CompletedTasks : Fragment() {

    private var binding: FragmentCompleteTasksBinding? = null
    private val taskBinding get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompleteTasksBinding.inflate(inflater, container, false)

        val taskImageResource = R.drawable.to_do_list
        val taskStatusImageResource = R.drawable.tick
        val taskRecycler = binding?.recyclerTasks
        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        taskModelArrayList.add(
            TasksModel("DSA in Java", "Status: InActive", "Date: 12-01-24", taskImageResource, taskStatusImageResource)
        )
        taskModelArrayList.add(
            TasksModel("DSA in Java", "Status: InActive", "Date: 19-01-24", taskImageResource, taskStatusImageResource)
        )
        taskModelArrayList.add(
            TasksModel("DSA in Java", "Status: InActive", "Date: 14-02-24", taskImageResource, taskStatusImageResource)
        )
        taskModelArrayList.add(
            TasksModel("DSA in Java", "Status: InActive", "Date: 15-02-24", taskImageResource, taskStatusImageResource)
        )


        val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskRecycler?.layoutManager = linearLayoutManager
        taskRecycler?.adapter = taskAdapter
        return binding?.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}