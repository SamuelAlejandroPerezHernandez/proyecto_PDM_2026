package com.programacionmovilprimeraapp.features.home.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.NoteBottomSheet
import com.programacionmovilprimeraapp.core.ui.components.TaskBottomSheet
import com.programacionmovilprimeraapp.core.ui.components.TaskFloatingButtom
import com.programacionmovilprimeraapp.features.home.home.Categories.CATEGORIA_NOTAS
import com.programacionmovilprimeraapp.features.home.home.Categories.CATEGORIA_TAREAS
import com.programacionmovilprimeraapp.features.home.home.HomeViewModel

object Categories{
    const val CATEGORIA_TAREAS = "5d92295a-efee-4629-90c1-7ac1a1e467eb"
    const val CATEGORIA_NOTAS = "91ab95dc-24d5-4bee-b0b2-e920fff44e3e"
}

@Composable
fun Home(
    goToTaskScreen: () -> Unit,
    goToNoteScreen: () -> Unit
){

    val viewModel: HomeViewModel = viewModel()
    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()

    val insertTask = {
        categoryId: String, title: String, description: String, dueDate: String ->
        viewModel.InsertTask(categoryId, title, description, dueDate)
    }

    val insertNote = {
            categoryId: String, title: String, content: String ->
        viewModel.InsertNote(categoryId, title, content)
    }

    var categoryId by rememberSaveable() { mutableStateOf<String?>(null) }

    val categorySelected = {
        category: String -> categoryId = category
    }

    val onDismiss = {
        categoryId = null
    }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar(goToTaskScreen, goToNoteScreen) },
        floatingActionButton = { TaskFloatingButtom(
            Categories.CATEGORIA_TAREAS,
            Categories.CATEGORIA_NOTAS,
            categorySelected,
        ) }
    ){
        innerPadding ->
        HomeContent(innerPadding)

        if(categoryId != null){
            if(categoryId == CATEGORIA_TAREAS){
                TaskBottomSheet(insertTask, categoryId, onDismiss, saveMessage)
            }
            else if(categoryId == CATEGORIA_NOTAS){
                NoteBottomSheet(insertNote, categoryId, onDismiss, saveMessage)
            }


        }
    }
}

@Composable
fun HomeContent(
    padding: PaddingValues
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){

    }
}