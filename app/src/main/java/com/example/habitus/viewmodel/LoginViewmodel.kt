package com.example.habitus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitus.model.Usuario
import com.example.habitus.repository.UsuarioRepository

class LoginViewmodel : ViewModel() {

    private val repository = UsuarioRepository()

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun login(username: String, senha: String, onSuccess: (String) -> Unit){
        if (username.isEmpty() || senha.isEmpty()) {
            _erro.postValue("Username e senha são obrigatórios.")
            return
        }

        repository.login(username, senha) { _, erroMsg ->
            Log.e("LOGIN_DEBUG","CLICOU")
            if (erroMsg != null) {
                _erro.postValue(erroMsg)
            } else {
                onSuccess("Logado com Sucesso")
            }
        }
    }
}