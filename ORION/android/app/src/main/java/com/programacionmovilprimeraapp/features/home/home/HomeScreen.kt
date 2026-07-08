package com.programacionmovilprimeraapp.features.home.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.NoteBottomSheet
import com.programacionmovilprimeraapp.core.ui.components.TaskBottomSheet
import com.programacionmovilprimeraapp.core.ui.components.TaskFloatingButtom
import com.programacionmovilprimeraapp.features.home.home.Categories.CATEGORIA_NOTAS
import com.programacionmovilprimeraapp.features.home.home.Categories.CATEGORIA_TAREAS
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.orionnotes.R

object Categories{
    const val CATEGORIA_TAREAS = "5d92295a-efee-4629-90c1-7ac1a1e467eb"
    const val CATEGORIA_NOTAS = "91ab95dc-24d5-4bee-b0b2-e920fff44e3e"
}

@Composable
fun Home(
    goToTaskScreen: () -> Unit,
    goToNoteScreen: () -> Unit,
    goToPerfil: () -> Unit,
    goToTaskDetail: (String) -> Unit,
    goToNoteDetail: (String) -> Unit
){
    val viewModel: HomeViewModel = viewModel()
    val save by viewModel.saving.collectAsState()
    val saveMessage by viewModel.savingMessage.collectAsState()

    val upcomingTasks by viewModel.upcomingTasks.collectAsState()
    val recentNotes by viewModel.recentNotes.collectAsState()

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
        topBar = { HomeTopBar(goToPerfil) },
        bottomBar = { HomeBottomBar(goToTaskScreen, goToNoteScreen) },
        floatingActionButton = { TaskFloatingButtom(
            Categories.CATEGORIA_TAREAS,
            Categories.CATEGORIA_NOTAS,
            categorySelected,
        ) }
    ){
            innerPadding ->
        HomeContent(
            padding = innerPadding,
            upcomingTasks = upcomingTasks,
            recentNotes = recentNotes,
            goToTaskDetail = goToTaskDetail,
            goToNoteDetail = goToNoteDetail
        )
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
    padding: PaddingValues,
    upcomingTasks: List<TaskModel>,
    recentNotes: List<NoteModel>,
    goToTaskDetail: (String) -> Unit,
    goToNoteDetail: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        item {
            Text(
                text = "Tareas próximas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.carbonBlack)
            )
        }

        if(upcomingTasks.isEmpty()){
            item {
                Text(
                    text = "No tienes tareas próximas",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.coolSteal)
                )
            }
        } else {
            items(upcomingTasks){
                    task ->
                SummaryCard(
                    title = task.title,
                    subtitle = task.dueDate.take(10),
                    onClick = { goToTaskDetail(task.id) }
                )
            }
        }

        item {
            Text(
                text = "Notas recientes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.carbonBlack),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        if(recentNotes.isEmpty()){
            item {
                Text(
                    text = "No tienes notas recientes",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.coolSteal)
                )
            }
        } else {
            items(recentNotes){
                    note ->
                SummaryCard(
                    title = note.title,
                    subtitle = note.content.take(40),
                    onClick = { goToNoteDetail(note.id) }
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
){
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.carbonBlack)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            if(!subtitle.isNullOrBlank()){
                Text(
                    text = subtitle,
                    color = colorResource(id = R.color.pearlAqua),
                    fontSize = 13.sp,
                    maxLines = 1
                )
            }
        }
    }
}