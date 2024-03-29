package com.example.todoapp.model

import android.widget.ImageView

class TasksModel(
    private var taskTitle: String,
    private var taskStatus: String,
    private var taskDate: String,
    private var taskImage: Int,
    private var taskStatusImage: Int
) {

    // Getter and Setter
    fun getTaskTitle(): String {
        return taskTitle
    }

    fun setTaskTitle(taskTitle: String) {
        this.taskTitle = taskTitle
    }

    fun getTaskStatus(): String {
        return taskStatus
    }

    fun setTaskStatus(taskStatus: String) {
        this.taskStatus = taskStatus
    }

    fun getTaskDate(): String {
        return taskDate
    }

    fun setTaskDate(taskDate: String) {
        this.taskDate = taskDate
    }

    fun getTaskImage(): Int {
        return taskImage
    }

    fun setTaskImage(taskImage: Int) {
        this.taskImage = taskImage
    }

    fun getTaskStatusImg(): Int {
        return taskStatusImage
    }

    fun setTaskStatusImg(taskStatusImage: Int) {
        this.taskStatusImage = taskStatusImage
    }


}
