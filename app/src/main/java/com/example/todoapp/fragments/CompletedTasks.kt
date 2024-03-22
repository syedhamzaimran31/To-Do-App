package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding = FragmentCompleteTasksBinding.inflate(inflater,container,false)

        val taskRecycler = binding?.recyclerTasks

        val taskModelArrayList: ArrayList<TasksModel> = ArrayList()
        taskModelArrayList.add(TasksModel("DSA in Java", true, "12-02-24"))
        taskModelArrayList.add(TasksModel("DSA in Java", true, "12-02-24"))
        taskModelArrayList.add(TasksModel("DSA in Java", true, "12-02-24"))
        taskModelArrayList.add(TasksModel("DSA in Java", true, "12-02-24"))


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