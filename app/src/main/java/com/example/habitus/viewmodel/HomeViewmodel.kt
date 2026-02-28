package com.example.habitus.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitus.model.Tarefa
import com.example.habitus.repository.TarefaRepository
import com.example.habitus.repository.UsuarioRepository

class HomeViewModel : ViewModel() {

    private val repository = TarefaRepository()
    private val usuarioRepository = UsuarioRepository()

    private val _tarefas = MutableLiveData<List<Tarefa>>()
    val tarefas: LiveData<List<Tarefa>> = _tarefas

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    private val _notificar = MutableLiveData<Tarefa>()
    val notificar: LiveData<Tarefa> = _notificar

    private val _AtualizarNotificacao = MutableLiveData<Tarefa>()
    val AtualizarNotificacao: LiveData<Tarefa> = _AtualizarNotificacao

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

        repository.toggleTarefa(id) { tarefaAtualizada, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                tarefaAtualizada?.let {
                    _AtualizarNotificacao.postValue(it)
                }
                carregarTarefas()
            }
        }
    }

    fun logout(){
        usuarioRepository.logout() { msg ->
            _erro.postValue(msg)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun criarTarefa(descricao: String, dataHora: String) {
        if (descricao.isEmpty() || dataHora.isEmpty()) {
            _erro.postValue("Descrição e data/hora são obrigatórios.")
            return
        }

        val novaTarefa = Tarefa(descricao = descricao, datahora = dataHora, ativo = false)

        repository.criarTarefa(novaTarefa) { tarefaCriada, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                tarefaCriada?.let {
                    _notificar.postValue(it)
                }
                carregarTarefas()
            }
        }
    }
}