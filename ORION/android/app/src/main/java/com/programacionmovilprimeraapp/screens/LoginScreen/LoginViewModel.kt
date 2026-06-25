package com.programacionmovilprimeraapp.screens.LoginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.repository.AuthRepositoryImp
import com.programacionmovilprimeraapp.domain.AuthRepository
import com.programacionmovilprimeraapp.domain.LoginRequestModel
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel(){
    private val Repository: AuthRepository = AuthRepositoryImp()

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    val savingMessage = _savingMessage.asStateFlow()

    private val emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$".toRegex()

    private val _userVerified = MutableStateFlow(false)
    val userVerified = _userVerified.asStateFlow()

    fun LoginUser(Email: String, Password: String){
        if (Password.length < 6) {
            _savingMessage.value = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        if (!emailRegex.matches(Email)) {
            _savingMessage.value = "Por favor, introduce un correo electrónico válido"
            return
        }

        viewModelScope.launch {
            _saving.value = true

            val loginPost = LoginRequestModel(
                email = Email,
                password = Password
            )

            Repository.LoginUser(loginPost)
                .onSuccess {
                        response ->
                    MyApp.sessionManager.saveToken(response.token)
                    _userVerified.value = true
                    _savingMessage.value = "Usuario autenticado con exito"
                }
                .onFailure {
                        e ->
                    _savingMessage.value = "Error al autenticar el usuario: ${e.message}"
                }

            _saving.value = false
        }
    }
}