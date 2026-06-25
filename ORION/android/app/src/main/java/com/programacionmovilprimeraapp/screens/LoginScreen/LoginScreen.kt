package com.programacionmovilprimeraapp.screens.LoginScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Login(
    goToRegisterScreen: () -> Unit,
    goToHome: () -> Unit
){
    val viewModel: LoginViewModel = viewModel()
    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()
    val userVerified by viewModel.userVerified.collectAsState()

    val authUser = {
            email: String, password: String ->
        viewModel.LoginUser(email, password)
    }

    LaunchedEffect(userVerified){
        if(userVerified == true){
            goToHome()
        }
    }

    LoginForm(
        save,
        saveMessage,
        authUser,
        goToRegisterScreen
    )
}

@Composable
fun LoginForm(
    save: Boolean,
    saveMessage: String?,
    authUser: (String, String) -> Unit,
    goToRegisterScreen: () -> Unit
){

    var emailText by rememberSaveable() { mutableStateOf("") }
    var passwordText by rememberSaveable() { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it},
                label = { Text(text = "Correo Elextronico") },
                singleLine = true
            )
        }

        item{
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it},
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
        }

        item{
            Button(
                onClick = {
                    authUser(emailText, passwordText)
                }
            ) {
                Text(text = "Iniciar Sesion")
            }
        }

        item {
            Text( text = "no tienes una cuenta?")
            Button(
                onClick = { goToRegisterScreen() }
            ) {
                Text(text = "Registrarse")
            }
        }
    }
}
