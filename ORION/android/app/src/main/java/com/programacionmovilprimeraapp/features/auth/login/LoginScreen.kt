package com.programacionmovilprimeraapp.features.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.orionnotes.R

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
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "¡Bienvenido de vuelta!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.carbonBlack),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Text(text = "Inicia sesión para continuar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.coolSteal),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(text = "Correo electronico",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.carbonBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it},
                placeholder = {Text("ejemplo@correo.com", color = colorResource(id = R.color.coolSteal))},
                label = { Text(text = "Correo Elextronico") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.dustyGrape),
                    unfocusedBorderColor = colorResource(id = R.color.coolSteal).copy(alpha = 0.5f),
                    focusedLabelColor = colorResource(id = R.color.dustyGrape)
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Contraseña",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.carbonBlack),
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it},
                label = { Text("Contraseña") },
                singleLine = true,

                visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                trailingIcon = {
                    val image = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(
                        onClick = {passwordVisible = !passwordVisible}
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = description,
                            tint = colorResource(id = R.color.coolSteal)
                        )
                    }
                },


                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.dustyGrape),
                    unfocusedBorderColor = colorResource(id = R.color.coolSteal).copy(alpha = 0.5f),

                    )
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = !saveMessage.isNullOrBlank(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = saveMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }

            Button(
                onClick = {
                    authUser(emailText, passwordText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dustyGrape),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Iniciar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta? ",
                    color = colorResource(id = R.color.coolSteal),
                    fontSize = 15.sp
                )

                TextButton(
                    onClick = { goToRegisterScreen() }
                ) {
                    Text(
                        text = "Crear una",
                        color = colorResource(id = R.color.pearlAqua),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
