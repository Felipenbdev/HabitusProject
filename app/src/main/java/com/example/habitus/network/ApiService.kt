package com.example.habitus.network

import com.example.habitus.entities.Tarefa
import com.example.habitus.entities.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // Usuários
    @GET("usuarios")
    fun listarUsuarios(): Call<List<Usuario>>

    @GET("usuarios/{id}")
    fun buscarUsuario(@Path("id") id: Long): Call<Usuario>

    @POST("usuarios")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>

    @DELETE("usuarios/{id}")
    fun deletarUsuario(@Path("id") id: Long): Call<Void>

    // Tarefas
    @GET("tarefas")
    fun listarTarefas(): Call<List<Tarefa>>

    @GET("tarefas/{id}")
    fun buscarTarefa(@Path("id") id: Long): Call<Tarefa>

    @POST("tarefas")
    fun criarTarefa(@Body tarefa: Tarefa): Call<Tarefa>

    @DELETE("tarefas/{id}")
    fun deletarTarefa(@Path("id") id: Long): Call<Void>

    @GET("tarefas/usuario/{usuarioId}")
    fun listarTarefasDoUsuario(@Path("usuarioId") usuarioId: Long): Call<List<Tarefa>>

    @DELETE("tarefas/usuario/{usuarioId}/tarefa/{tarefaId}")
    fun deletarTarefaDoUsuario(
        @Path("usuarioId") usuarioId: Long,
        @Path("tarefaId") tarefaId: Long
    ): Call<Void>
}