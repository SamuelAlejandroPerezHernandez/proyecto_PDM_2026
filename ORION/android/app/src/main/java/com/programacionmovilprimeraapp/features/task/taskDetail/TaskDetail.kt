package com.programacionmovilprimeraapp.features.task.taskDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    id: String
){
    val viewModel: TaskDetailViewModel = viewModel()
    val task by viewModel.responseTaskModel.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTaskDetail(id)
    }

    val onRetry = {
        viewModel.loadTaskDetail(id)
    }

    Scaffold(
        topBar = {}
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
                    TaskDetailContent(innerPadding, task)
                }
            }
        }
    }
}

@Composable
fun TaskDetailContent(
    padding: PaddingValues,
    task: TaskDetailModel?
){
    LazyColumn(

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
            Text(text = task?.taskDetail?.isCompleted.toString()?: "")
        }
    }
}

