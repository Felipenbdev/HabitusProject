package com.example.habitus.repository

import com.example.habitus.model.Usuario
import com.example.habitus.network.RetrofitInstance
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

class UsuarioRepository {
    private val api = RetrofitInstance.api

    fun criarUsuario(usuario: Usuario, callback: (Usuario?, String?) -> Unit){
        api.criarUsuario(usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun login(username: String, senha: String, callback: (Usuario?, String?) -> Unit){
        api.login(username, senha).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
}