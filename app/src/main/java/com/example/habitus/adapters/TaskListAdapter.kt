package com.example.habitus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.habitus.R
import com.example.habitus.entities.Tarefa

class TaskListAdapter(
    private var tasks: List<Tarefa>,
    private val onTaskToggled: (Tarefa) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckbox: CheckBox = view.findViewById(R.id.taskCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        // Remove listener antigo para evitar bug de reciclagem
        holder.taskCheckbox.setOnCheckedChangeListener(null)

        // Define texto e estado
        holder.taskCheckbox.text = task.descricao
        holder.taskCheckbox.isChecked = task.ativo ?: false

        // Novo listener
        holder.taskCheckbox.setOnCheckedChangeListener { _, _ ->
            onTaskToggled(task)
        }
    }

    fun updateSingleTask(updated: Tarefa): Int {
        val index = tasks.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            val mutableList = tasks.toMutableList()
            mutableList[index] = updated
            tasks = mutableList
            notifyItemChanged(index) // só atualiza a posição
        }
        return index
    }

    override fun getItemCount() = tasks.size

    // Função para atualizar a lista de tarefas no adapter
    fun updateTasks(newTasks: List<Tarefa>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}