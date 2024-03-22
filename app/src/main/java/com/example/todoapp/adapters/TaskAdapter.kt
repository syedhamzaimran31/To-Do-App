package com.example.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.TasksModel

class TaskAdapter(private val context: Context, courseModelArrayList: ArrayList<TasksModel>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val taskModelArrayList: ArrayList<TasksModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val model: TasksModel = taskModelArrayList[position]
        holder.courseTitleTV.text = model.getTaskTitle()
        holder.courseStatusTV.text = "" + model.getTaskStatus()
        holder.courseDateTV.text = model.getTaskDate()
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return taskModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseDateTV: TextView
        val courseTitleTV: TextView
        val courseStatusTV: TextView

        init {
            courseDateTV = itemView.findViewById(R.id.dateValue)
            courseTitleTV = itemView.findViewById(R.id.titleValue)
            courseStatusTV = itemView.findViewById(R.id.statusValue)
        }
    }

    // Constructor
    init {
        this.taskModelArrayList = courseModelArrayList
    }
}