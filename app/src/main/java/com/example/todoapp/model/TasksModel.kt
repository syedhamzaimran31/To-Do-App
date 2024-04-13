package com.example.todoapp.model

import android.widget.ImageView
import java.time.Duration


class TasksModel(
    private var id: String,
    private var userId: String,
    private var taskName: String,
    private var taskStatus: String,
    private var createdAt: String,
    private var taskImage: String,
    private var taskDuration: String,
    private var taskDescription: String
) {
    constructor() : this("", "", "", "", "", "", "", "")

    // Getters
    fun getId(): String {
        return id
    }

    fun getUserId(): String {
        return userId
    }

    fun getTaskName(): String {
        return taskName
    }

    fun getTaskStatus(): String {
        return taskStatus
    }

    fun getCreatedAt(): String {
        return createdAt
    }

    fun getTaskImage(): String {
        return taskImage
    }

    fun getTaskDuration(): String {
        return taskDuration
    }

   fun getTaskDescription():String{
       return taskDescription
   }
    // Setters
    fun setId(id: String) {
        this.id = id
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun setTaskName(taskName: String) {
        this.taskName = taskName
    }

    fun setTaskStatus(taskStatus: String) {
        this.taskStatus = taskStatus
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun setTaskImage(taskImage: String) {
        this.taskImage = taskImage
    }

    fun setTaskDuration(taskDuration: String) {
        this.taskDuration = taskDuration
    }

    fun setTaskDescription(taskDescription: String) {
        this.taskDescription = taskDescription
    }

}
