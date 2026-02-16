package com.example.habitus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitus.model.Tarefa
import com.example.habitus.repository.TarefaRepository

class HomeViewModel : ViewModel() {

    private val repository = TarefaRepository()

    private val _tarefas = MutableLiveData<List<Tarefa>>()
    val tarefas: LiveData<List<Tarefa>> = _tarefas

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun carregarTarefas() {
        repository.listarTarefas { lista, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                _tarefas.postValue(lista ?: emptyList())
            }
        }
    }

    fun toggleTarefa(tarefa: Tarefa) {
        val id = tarefa.id ?: return

        repository.toggleTarefa(id) { _, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                carregarTarefas()
            }
        }
    }

    fun criarTarefa(descricao: String, dataHora: String) {
        if (descricao.isEmpty() || dataHora.isEmpty()) {
            _erro.postValue("Descrição e data/hora são obrigatórios.")
            return
        }

        val novaTarefa = Tarefa(descricao = descricao, datahora = dataHora, ativo = false)

        repository.criarTarefa(novaTarefa) { _, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                carregarTarefas()
            }
        }
    }
}