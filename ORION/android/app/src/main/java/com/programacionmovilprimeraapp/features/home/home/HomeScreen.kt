package com.programacionmovilprimeraapp.features.home.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.data.local.SessionManager
import com.programacionmovilprimeraapp.core.ui.components.TaskBottomSheet
import com.programacionmovilprimeraapp.core.ui.components.TaskFloatingButtom
import com.programacionmovilprimeraapp.features.note.presentation.AddNoteBottomSheet
import com.programacionmovilprimeraapp.features.note.presentation.NotesScreen
import com.programacionmovilprimeraapp.features.note.presentation.NoteViewModel

object Categories {
    const val CATEGORIA_TAREAS = "5d92295a-efee-4629-90c1-7ac1a1e467eb"
    const val CATEGORIA_NOTAS = "91ab95dc-24d5-4bee-b0b2-e920fff44e3e"
}

@Composable
fun Home(
    goToTaskScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel()
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)

    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()

    val noteSaveMessage by noteViewModel.savingMessage.collectAsState()
    val noteSaveSuccess by noteViewModel.saveSuccess.collectAsState()

    val insertTask = { categoryId: String, title: String, description: String, dueDate: String ->
        viewModel.InsertTask(categoryId, title, description, dueDate)
    }
    var seccionActiva by rememberSaveable { mutableStateOf("INICIO") }
    var categoryId by rememberSaveable { mutableStateOf<String?>(null) }

    val categorySelected = { category: String ->
        categoryId = category
    }

    val onDismiss = {
        categoryId = null
        noteViewModel.clearSavingMessage()
    }

    LaunchedEffect(noteSaveSuccess) {
        if (noteSaveSuccess) {
            categoryId = null
            noteViewModel.resetSaveSuccess()
        }
    }

    Scaffold(
        topBar = { OrionTopBar() },
        bottomBar = {
            HomeBottomBar(
                seccionActiva = seccionActiva,
                goToTaskScreen = { goToTaskScreen() },
                goToHomeScreen = { seccionActiva = "INICIO" },
                goToProfileScreen = { seccionActiva = "PERFIL" },
                goToNotesScreen = { seccionActiva = "NOTAS" }
            )
        },
        floatingActionButton = {
            TaskFloatingButtom(
                Categories.CATEGORIA_TAREAS,
                Categories.CATEGORIA_NOTAS,
                categorySelected
            )
        }
    ) { innerPadding ->

        when (seccionActiva) {
            "NOTAS" -> {
                NotesScreen(
                    viewModel = noteViewModel,
                    paddingValues = innerPadding
                )
            }
            "PERFIL" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Configuración de Cuenta",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Si experimentas problemas con Supabase, cierra sesión e inicia de nuevo para limpiar las credenciales locales.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            val sessionManager = SessionManager.getInstance(context)
                            sessionManager.clearSession()
                            (context as? android.app.Activity)?.finish()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text("Cerrar Sesión", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Bienvenido a Orion",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
        if (categoryId == Categories.CATEGORIA_TAREAS) {
            TaskBottomSheet(insertTask, categoryId, onDismiss, saveMessage)
        }
        if (categoryId == Categories.CATEGORIA_NOTAS) {
            AddNoteBottomSheet(
                onDismiss = { onDismiss() },
                onSave = { title, content ->
                    noteViewModel.createNote(title, content, Categories.CATEGORIA_NOTAS)
                },
                saveMessage = noteSaveMessage
            )
        }
    }
}