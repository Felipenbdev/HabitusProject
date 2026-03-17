package com.example.habitus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitus.model.Usuario
import com.example.habitus.repository.UsuarioRepository

class RegisterViewmodel : ViewModel() {

    private val repository = UsuarioRepository()

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun criarUsuario(username: String, senha: String, confirmSenha: String, onSuccess: (String) -> Unit){
        if (username.isEmpty() || senha.isEmpty() || confirmSenha.isEmpty()) {
            _erro.postValue("Toodos os campos devem estar preenchidos!")
            return
        }
        if (senha != confirmSenha) {
            _erro.postValue("As senhas devem ser iguais!")
            return
        }
        val novoUsuario = Usuario(username = username,senha = senha)
        repository.criarUsuario(novoUsuario) { _, erroMsg ->
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                onSuccess("Registrado com Sucesso")
            }
        }
    }
}