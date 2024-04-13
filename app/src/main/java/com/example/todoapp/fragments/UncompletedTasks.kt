package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.databinding.FragmentUncompletedTasksBinding
import com.example.todoapp.model.TasksModel


class UncompletedTasks : Fragment() {

    private var binding: FragmentUncompletedTasksBinding? = null
    private val taskBinding get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUncompletedTasksBinding.inflate(inflater,container,false)

        val taskImageResource = R.drawable.to_do_list
        val taskStatusImageResource = R.drawable.cross
        val taskRecycler = binding?.recyclerTasks

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

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

//        taskRecycler?.layoutManager = linearLayoutManager
//        taskRecycler?.adapter = taskAdapter
        return binding?.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}