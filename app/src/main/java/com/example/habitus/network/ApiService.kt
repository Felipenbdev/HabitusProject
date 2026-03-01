package com.example.habitus.network

import com.example.habitus.model.AuthResponse
import com.example.habitus.model.LoginRequest
import com.example.habitus.model.Tarefa
import com.example.habitus.model.Usuario
import com.example.habitus.model.UsuarioResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // ---------------- Usuários ----------------

    @POST("usuarios/login")
    fun login(
        @Body request: LoginRequest
    ): Call<AuthResponse>

    @POST("usuarios")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>

    @GET("usuarios/me")
    fun me(): Call<UsuarioResponse>

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
