package com.example.habitus.repository

import com.example.habitus.model.Tarefa
import com.example.habitus.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TarefaRepository {
    private val api = RetrofitInstance.api

    fun listarTarefas(callback: (List<Tarefa>?, String?) -> Unit) {
        api.listarTarefas().enqueue(object : Callback<List<Tarefa>> {
            override fun onResponse(
                call: Call<List<Tarefa>>,
                response: Response<List<Tarefa>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Tarefa>>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun toggleTarefa(id: Long, callback: (Tarefa?, String?) -> Unit) {
        api.toggleTarefa(id).enqueue(object : Callback<Tarefa> {
            override fun onResponse(call: Call<Tarefa>, response: Response<Tarefa>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Tarefa>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun criarTarefa(tarefa: Tarefa, callback: (Tarefa?, String?) -> Unit) {
        api.criarTarefa(tarefa).enqueue(object : Callback<Tarefa> {
            override fun onResponse(call: Call<Tarefa>, response: Response<Tarefa>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Tarefa>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
}