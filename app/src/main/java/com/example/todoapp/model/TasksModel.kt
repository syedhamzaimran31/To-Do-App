package com.example.todoapp.model

class TasksModel(private var taskTitle: String, private var taskStatus: Boolean, private var taskDate: String) {

    // Getter and Setter
    fun getTaskTitle(): String {
        return taskTitle
    }

    fun setTaskTitle(taskTitle:String) {
        this.taskTitle = taskTitle
    }

    fun getTaskStatus(): Boolean {
        return taskStatus
    }

    fun setTaskStatus(taskStatus: Boolean) {
        this.taskStatus = taskStatus
    }

    fun getTaskDate(): String {
        return taskDate
    }

    fun setTaskDate(taskDate: String) {
        this.taskDate = taskDate
    }
}
