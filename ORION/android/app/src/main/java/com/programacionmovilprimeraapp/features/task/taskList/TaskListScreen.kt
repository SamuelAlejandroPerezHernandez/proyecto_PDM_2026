package com.programacionmovilprimeraapp.features.task.taskList

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.features.home.home.OrionTopBar
import com.programacionmovilprimeraapp.orionnotes.R
import com.programacionmovilprimeraapp.features.task.taskList.TaskListViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    goToDetail: (String) -> Unit
){
    val viewModel: TaskListViewModel = viewModel()
    val taskList by viewModel.taskList.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTask()
    }

    val onRetry = {
        viewModel.loadTask()
    }

    val update = {
        id: String, isCompleted: Boolean ->
        viewModel.updateTaskStatus(id, isCompleted)
    }

    Scaffold(
        topBar = { OrionTopBar() },
        bottomBar = { TaskListBottomBar() }
    ) {
        innerPadding ->
        when{
            loading -> {
                LoadingContent(innerPadding)
            }

            error != null -> {
                ErrorContent(error,innerPadding, onRetry)
            }

            else -> {
                PullToRefreshBox(
                    isRefreshing = refreshing,
                    onRefresh = { viewModel.refreshingTasks() },
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TaskListContent(taskList, innerPadding, goToDetail, update)
                }
            }
        }
    }
}

@Composable
fun TaskListContent(
    taskList: List<TaskModel>,
    padding: PaddingValues,
    goToDetail: (String) -> Unit,
    update: (String, Boolean) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(taskList){
            task ->

            var checkStatus by rememberSaveable { mutableStateOf(task.isCompleted) }

            if(task.isCompleted == false){
                Card(
                    onClick = { goToDetail(task.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.carbonBlack)),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {



                        Text(text = task.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)


                        Switch(
                            checked = checkStatus,
                            onCheckedChange = { change ->
                                checkStatus = change
                                update(task.id, change)
                            }
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun TaskListBottomBar(

){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource( id = R.color.carbonBlack )
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(top = 25.dp, bottom = 40.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null,
                    tint = colorResource( id = R.color.pearlAqua),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .size(30.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)

            ){
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}