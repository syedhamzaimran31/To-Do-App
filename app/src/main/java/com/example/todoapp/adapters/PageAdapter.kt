package com.example.todoapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.todoapp.fragments.CompletedTasks
import com.example.todoapp.fragments.UncompletedTasks

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }


    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return CompletedTasks()
            }
            1 -> {
                return UncompletedTasks()
            }
            else -> {
                return CompletedTasks()
            }
        }
    }

}