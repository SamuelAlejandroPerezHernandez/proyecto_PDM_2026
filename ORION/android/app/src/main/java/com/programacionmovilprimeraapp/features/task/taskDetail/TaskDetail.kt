package com.programacionmovilprimeraapp.features.task.taskDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.DetailBottomBar
import com.programacionmovilprimeraapp.core.ui.components.DialogDateSelector
import com.programacionmovilprimeraapp.core.ui.components.DialogTimeSelector
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.core.ui.components.OrionTopBar

import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.orionnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    id: String,
    backToList: () -> Unit,
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
    goToNoteList: () -> Unit
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
        topBar = { OrionTopBar(backToList) },
        bottomBar = { DetailBottomBar(goToHome, goToTaskList, goToNoteList) }

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
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item{
            Column{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notes,
                        contentDescription = null,
                        tint = colorResource(id = R.color.carbonBlack),
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Titulo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.carbonBlack)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = task?.taskDetail?.title ?: "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.carbonBlack),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }

        item{
            Column{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Description,
                        contentDescription = null,
                        tint = colorResource(id = R.color.carbonBlack),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "Descripcion",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.carbonBlack)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(id = R.color.carbonBlack))
                        .heightIn(min = 140.dp)
                        .border(
                            width = 2.5.dp,
                            color = colorResource(id = R.color.pearlAqua),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ){
                    Text(text = task?.taskDetail?.description?: "",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white),
                        lineHeight = 24.sp
                    )
                }
            }
        }

        item{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarMonth,
                        contentDescription = null,
                        tint = colorResource(id = R.color.coolSteal),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(text = task?.taskDetail?.dueDate?: "",
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.coolSteal),
                        lineHeight = 22.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if(task?.taskDetail?.isCompleted == true){
                                Color(0xFF2E7D32)
                            }
                            else{
                                Color(0xFFC62828)
                            }
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.carbonBlack),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    if(task?.taskDetail?.isCompleted == true){
                        Text(text = "Completada",
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp)
                    }else{
                        Text(text = "Incompletada",
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        sheetStatus(true)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(text = "Actualizar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp)
                    }
                }

                OutlinedButton(
                    onClick = {
                        delete(task?.taskDetail?.id?: "")
                        backToList()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))


                        Text(text = "Eliminar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp)
                    }
                }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Título de la tarea" )},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.dustyGrape),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorResource(id = R.color.dustyGrape)
                    )
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Descripción" )},
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.dustyGrape),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorResource(id = R.color.dustyGrape)
                    )
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ){
                        DialogDateSelector(calendarioDate)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ){
                        DialogTimeSelector(ClockTime)
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if(dateT != null && timeT != null){
                            dueDate = "${dateT}T${timeT}:00Z"
                        }

                        update(task?.taskDetail?.id?: "", title, description, dueDate)
                        sheetStatus(false)
                    },

                    modifier = Modifier
                            .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(56.dp),

                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.dustyGrape)
                    )
                ) {
                    Text(text = "Editar tarea",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}




