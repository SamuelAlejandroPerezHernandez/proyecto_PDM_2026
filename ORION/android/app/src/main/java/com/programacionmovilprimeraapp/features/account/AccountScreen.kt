package com.programacionmovilprimeraapp.features.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.features.home.home.HomeBottomBar
import com.programacionmovilprimeraapp.features.home.home.OrionTopBar

// Colores propios de esta pantalla, tomados del mockup de diseño.
private val ScreenBackground = Color(0xFFF4F2F6)
private val EmailBoxBackground = Color(0xFFDCF3E3)
private val EmailTextColor = Color(0xFF1F7A43)
private val TitleColor = Color(0xFF1C1B1F)
private val LabelColor = Color(0xFF49454F)
private val StatLabelColor = Color(0xFF79747E)
private val LogoutColor = Color(0xFFD32F2F)

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
    val email by viewModel.email.collectAsState()
    val taskCount by viewModel.taskCount.collectAsState()
    val noteCount by viewModel.noteCount.collectAsState()
    val loading by viewModel.loading.collectAsState()

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
            email = email,
            taskCount = taskCount,
            noteCount = noteCount,
            loading = loading,
            onLogout = { viewModel.cerrarSesion() }
        )
    }
}

@Composable
fun AccountContent(
    padding: PaddingValues,
    email: String?,
    taskCount: Int,
    noteCount: Int,
    loading: Boolean,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(padding)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Mi cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = TitleColor
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Correo electrónico",
            fontSize = 15.sp,
            color = LabelColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(EmailBoxBackground)
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Text(
                text = email ?: "Cargando...",
                fontSize = 17.sp,
                color = EmailTextColor
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                value = if (loading) "…" else taskCount.toString(),
                label = "tareas",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = if (loading) "…" else noteCount.toString(),
                label = "notas",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = LogoutColor),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Cerrar sesión",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = TitleColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 15.sp,
            color = StatLabelColor
        )
    }
}