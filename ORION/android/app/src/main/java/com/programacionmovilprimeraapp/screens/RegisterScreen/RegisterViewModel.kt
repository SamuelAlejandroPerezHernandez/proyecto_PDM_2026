package com.programacionmovilprimeraapp.screens.RegisterScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.repository.AuthRepositoryImp
import com.programacionmovilprimeraapp.domain.AuthRepository
import com.programacionmovilprimeraapp.domain.RegisterRequestModel
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel(){

    private val Repository: AuthRepository = AuthRepositoryImp()

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    val savingMessage = _savingMessage.asStateFlow()

    fun RegisterUser(Email: String, Password: String){
        if (Password.length < 6) {
            _savingMessage.value = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        viewModelScope.launch {
            _saving.value = true

            val registerPost = RegisterRequestModel(
                email = Email,
                password = Password
            )

            Repository.RegisterNewUser(registerPost)
                .onSuccess {
                        response ->
                    _savingMessage.value = "Usuario registrado con exito"
                }
                .onFailure {
                        e ->
                    _savingMessage.value = "Error al crear el usuario: ${e.message}"
                }

            _saving.value = false
        }
    }
}
