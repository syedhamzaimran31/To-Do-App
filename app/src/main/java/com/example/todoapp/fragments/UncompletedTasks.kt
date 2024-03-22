package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.databinding.FragmentCompleteTasksBinding
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

        val taskRecycler = binding?.recyclerTasks

        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        taskModelArrayList.add(TasksModel("DSA in Java", false, "12-02-24"))
        taskModelArrayList.add(TasksModel("DSA in Java", false, "12-02-24"))
        taskModelArrayList.add(TasksModel("DSA in Java", false, "12-02-24"))


        val taskAdapter = TaskAdapter(requireContext(), taskModelArrayList)

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskRecycler?.layoutManager = linearLayoutManager
        taskRecycler?.adapter = taskAdapter
        return binding?.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}