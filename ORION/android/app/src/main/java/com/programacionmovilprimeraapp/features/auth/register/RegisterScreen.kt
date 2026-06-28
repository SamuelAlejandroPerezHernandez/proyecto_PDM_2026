package com.programacionmovilprimeraapp.features.auth.register


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.features.auth.register.RegisterViewModel

@Composable
fun Register(
    backToLogin: () -> Unit
){
    val viewModel: RegisterViewModel = viewModel()
    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()

    val registerUser = {
            email: String, password: String ->
        viewModel.RegisterUser(email, password)
    }

    RegisterForm(
        save,
        saveMessage,
        registerUser
    )
}

@Composable
fun RegisterForm(
    save: Boolean,
    saveMessage: String?,
    registerUser: (String, String) -> Unit
){

    var emailText by rememberSaveable() { mutableStateOf("") }
    var passwordText by rememberSaveable() { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
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
                onClick = { registerUser( emailText, passwordText) }
            ) {
                Text(text = "Registarse")
            }
        }

        item {
            saveMessage?.let {
                Text(text = it)
            }
        }
    }
}