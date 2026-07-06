package com.programacionmovilprimeraapp.features.task.taskDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.DialogDateSelector
import com.programacionmovilprimeraapp.core.ui.components.DialogTimeSelector
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.features.home.home.OrionTopBar
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.orionnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    id: String,
    backToList: () -> Unit
){
    val viewModel: TaskDetailViewModel = viewModel()
    val task by viewModel.responseTaskModel.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()
    val error by viewModel.error.collectAsState()
    var onDimmisStatus by rememberSaveable() { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadTaskDetail(id)
    }

    val onRetry = {
        viewModel.loadTaskDetail(id)
    }

    val update = {
        id: String, title: String, description: String, date: String ->
        viewModel.updateTaskDetail(id, title, description, date)
    }

    val delete = {
        id: String -> viewModel.deleteTaskDetail(id)
    }

    val sheetStatus = {
        status: Boolean -> onDimmisStatus = status
    }

    Scaffold(
        topBar = { OrionTopBar() },
        bottomBar = { TaskListBottomBar() }

    ) {
        innerPadding ->
        when {
            loading -> {
                LoadingContent(innerPadding)
            }
            error != null -> {
                ErrorContent(error, innerPadding, onRetry)
            }
            else -> {
                PullToRefreshBox(
                    isRefreshing = refreshing,
                    onRefresh = { viewModel.refreshingTaskDetail(id)},
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TaskDetailContent(innerPadding, task, sheetStatus, delete, backToList)

                    if(onDimmisStatus != false){
                        TaskUpdatateBottomSheet(sheetStatus, task, update)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskDetailContent(
    padding: PaddingValues,
    task: TaskDetailModel?,
    sheetStatus: (Boolean) -> Unit,
    delete: (String) -> Unit,
    backToList: () -> Unit
){
    LazyColumn(
        modifier = Modifier
            .padding(padding)
    ) {
        item{
            Text(text = task?.taskDetail?.title?: "")
        }

        item{
            Text(text = task?.taskDetail?.description?: "")
        }

        item{
            Text(text = task?.taskDetail?.dueDate?: "")
        }

        item{
            Text(text = task?.taskDetail?.isCompleted?.toString() ?: "")
        }

        item {
            Button(
                onClick = {
                    sheetStatus(true)
                }
            ) {
                Text(text = "Actualizar")
            }
        }

        item {
            Button(
                onClick = {
                    delete(task?.taskDetail?.id?: "")
                    backToList()
                }
            ) {
                Text(text = "Eliminar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskUpdatateBottomSheet(
    sheetStatus: (Boolean) -> Unit,
    task: TaskDetailModel?,
    update: (String, String, String, String) -> Unit
){
    var title by rememberSaveable() { mutableStateOf(task?.taskDetail?.title?: "") }
    var description by rememberSaveable() { mutableStateOf(task?.taskDetail?.description?: "") }

    var dueDate by rememberSaveable() { mutableStateOf(task?.taskDetail?.dueDate?: "") }

    var dateT by rememberSaveable() { mutableStateOf<String?>(null) }
    var timeT by rememberSaveable() { mutableStateOf<String?>(null) }

    val calendarioDate = {
        date: String -> dateT = date
    }

    val ClockTime = {
        time: String -> timeT = time
    }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { sheetStatus(false) },
        sheetState = sheetState
    ) {
        LazyColumn(

        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = task?.taskDetail?.title?: "" )}
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = task?.taskDetail?.description?: "" )}
                )
            }

            item {
                DialogDateSelector(calendarioDate)
            }

            item {
                DialogTimeSelector(ClockTime)
            }

            item {
                Button(
                    onClick = {
                        if(dateT != null && timeT != null){
                            dueDate = "${dateT}T${timeT}:00Z"
                        }

                        update(task?.taskDetail?.id?: "", title, description, dueDate)
                        sheetStatus(false)
                    }
                ) {
                    Text(text = "Actualizar")
                }
            }
        }
    }
}




