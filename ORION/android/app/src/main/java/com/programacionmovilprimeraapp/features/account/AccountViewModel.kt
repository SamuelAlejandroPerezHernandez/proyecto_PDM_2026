package com.programacionmovilprimeraapp.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _sessionClosed = MutableStateFlow(false)
    val sessionClosed = _sessionClosed.asStateFlow()

    fun cerrarSesion() {
        viewModelScope.launch {
            MyApp.Companion.sessionManager.clearSession()
            _sessionClosed.value = true
        }
    }
}