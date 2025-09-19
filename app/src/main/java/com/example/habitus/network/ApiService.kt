package com.example.habitus.network

import com.example.habitus.entities.Tarefa
import com.example.habitus.entities.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // ---------------- Usuários ----------------
    @POST("usuarios")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("usuarios/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("senha") senha: String
    ): Call<Usuario>

    @POST("usuarios/logout")
    fun logout(): Call<String>

    @GET("usuarios/me")
    fun me(): Call<Usuario>

    // ---------------- Tarefas ----------------
    @GET("tarefas")
    fun listarTarefas(): Call<List<Tarefa>>

    @POST("tarefas")
    fun criarTarefa(@Body tarefa: Tarefa): Call<Tarefa>

    @DELETE("tarefas/{id}")
    fun deletarTarefa(@Path("id") id: Long): Call<Void>

    @PATCH("tarefas/{id}/toggle")
    fun toggleTarefa(@Path("id") id: Long): Call<Tarefa>
}
