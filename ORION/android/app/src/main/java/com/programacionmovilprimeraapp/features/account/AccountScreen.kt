package com.programacionmovilprimeraapp.features.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.features.home.home.HomeBottomBar
import com.programacionmovilprimeraapp.features.home.home.OrionTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    goToTaskScreen: () -> Unit,
    goToNoteScreen: () -> Unit,
    goToAccountScreen: () -> Unit,
    goToLogin: () -> Unit
) {
    val viewModel: AccountViewModel = viewModel()
    val sessionClosed by viewModel.sessionClosed.collectAsState()

    LaunchedEffect(sessionClosed) {
        if (sessionClosed) {
            goToLogin()
        }
    }

    Scaffold(
        topBar = { OrionTopBar() },
        bottomBar = { HomeBottomBar(goToTaskScreen, goToNoteScreen, goToAccountScreen) }
    ) { innerPadding ->
        AccountContent(
            padding = innerPadding,
            onLogout = { viewModel.cerrarSesion() }
        )
    }
}

@Composable
fun AccountContent(
    padding: PaddingValues,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Configuración de Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(text = "Cerrar Sesión", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}