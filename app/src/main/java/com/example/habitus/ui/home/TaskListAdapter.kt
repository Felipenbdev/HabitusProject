package com.example.habitus.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.habitus.R
import com.example.habitus.model.Tarefa

// TaskListAdapter.kt
class TaskListAdapter(
    private val onTaskToggled: (Tarefa) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private var tasks: List<Tarefa> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckbox: CheckBox = view.findViewById(R.id.taskCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskCheckbox.apply {
            setOnCheckedChangeListener(null) // Evita loops de feedback
            text = task.descricao
            isChecked = task.ativo ?: false

            // Melhoria visual: Riscar o texto se estiver concluído
            paintFlags = if (isChecked) {
                paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            setOnClickListener { onTaskToggled(task) }
        }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Tarefa>) {
        val diffCallback = object : androidx.recyclerview.widget.DiffUtil.Callback() {
            override fun getOldListSize() = tasks.size
            override fun getNewListSize() = newTasks.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) = tasks[oldPos].id == newTasks[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) = tasks[oldPos] == newTasks[newPos]
        }
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffCallback)
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }
}