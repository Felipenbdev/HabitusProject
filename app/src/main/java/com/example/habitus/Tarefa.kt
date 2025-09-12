package com.example.habitus

data class Tarefa(
    val id: Long? = null,
    val descricao: String? = null,
    val datahora: String? = null,
    val ativo: Boolean? = null,
    val usuario: Usuario? = null
)