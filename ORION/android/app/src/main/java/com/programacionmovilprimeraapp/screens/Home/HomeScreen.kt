package com.programacionmovilprimeraapp.screens.Home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.orionnotes.R
import com.programacionmovilprimeraapp.screens.TaskBottomSheet
import com.programacionmovilprimeraapp.screens.NoteBottomSheet
import com.programacionmovilprimeraapp.domain.NoteModel

object Categories {
    const val CATEGORIA_TAREAS = "5d92295a-efee-4629-90cl-7aclale467eb"
    const val CATEGORIA_NOTAS = "91ab95dc-24d5-4bee-b0b2-e920fff44e3e"
}

@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()
    val notesList by viewModel.notesList.collectAsState()
    var currentScreen by rememberSaveable { mutableStateOf("home") }

    val insertTask = { categoryId: String, title: String, description: String, dueDate: String ->
        viewModel.InsertTask(categoryId, title, description, dueDate)
    }

    var categoryId by rememberSaveable { mutableStateOf<String?>(null) }
    val categorySelected = { category: String -> categoryId = category }
    val onDismiss = { categoryId = null }

    LaunchedEffect(saveMessage) {
        if (saveMessage == "Nota guardada con éxito" || saveMessage == "Tarea guardada con exito") {
            onDismiss()
        }
    }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = {
            HomeBottomBar(
                currentScreen = currentScreen,
                onTabSelected = { currentScreen = it }
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
        HomeContent(
            padding = innerPadding,
            currentScreen = currentScreen,
            notes = notesList
        )

        if (categoryId != null) {
            if (categoryId == Categories.CATEGORIA_NOTAS) {
                NoteBottomSheet(
                    insertNote = { idCat, title, content ->
                        viewModel.InsertNote(idCat, title, content)
                    },
                    categoryId = categoryId,
                    onDismiss = onDismiss,
                    saveMessage = saveMessage
                )
            } else {
                TaskBottomSheet(insertTask, categoryId, onDismiss, saveMessage)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    padding: PaddingValues,
    currentScreen: String,
    notes: List<NoteModel>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        if (currentScreen == "home") {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "Mis Tareas",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        } else if (currentScreen == "notes") {
            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay notas disponibles", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "Mis Notas",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(notes) { note ->
                        var isExpanded by remember { mutableStateOf(false) }

                        Card(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.carbonBlack).copy(alpha = 0.4f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = note.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = colorResource(id = R.color.pearlAqua),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                                        contentDescription = if (isExpanded) "Colapsar" else "Expandir",
                                        tint = Color.Gray
                                    )
                                }

                                if (isExpanded) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = note.content,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    currentScreen: String,
    onTabSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.carbonBlack))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onTabSelected("home") },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Tareas",
                    tint = if (currentScreen == "home") colorResource(id = R.color.pearlAqua) else Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(
                onClick = {  },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = "Calendario",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(
                onClick = { onTabSelected("notes") },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notas",
                    tint = if (currentScreen == "notes") colorResource(id = R.color.pearlAqua) else Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}