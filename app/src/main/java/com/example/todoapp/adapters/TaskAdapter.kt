package com.example.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.taskTitleTV.text = model.getTaskName()
        holder.taskStatusTV.text = "" + model.getTaskStatus()
        holder.taskDateTV.text = model.getCreatedAt()
        holder.taskImage.setImageResource(model.getTaskImage())
        holder.taskImageStatus.setImageResource(model.getTaskStatusImage())
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return taskModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskImage: ImageView
        val taskDateTV: TextView
        val taskTitleTV: TextView
        val taskStatusTV: TextView
        var taskImageStatus: ImageView

        init {
            taskImage = itemView.findViewById(R.id.taskImageValue)
            taskDateTV = itemView.findViewById(R.id.dateValue)
            taskTitleTV = itemView.findViewById(R.id.titleValue)
            taskStatusTV = itemView.findViewById(R.id.statusValue)
            taskImageStatus = itemView.findViewById(R.id.statusImageValue)
        }
    }

    // Constructor
    init {
        this.taskModelArrayList = courseModelArrayList
    }
}