package com.programacionmovilprimeraapp.features.notes.noteDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.DetailBottomBar
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.core.ui.components.OrionTopBar

import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    id: String,
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
    goToNoteList: () -> Unit,
    back: () -> Unit
){
    val viewModel: NoteDetailViewModel = viewModel()
    val note by viewModel.responseNoteModel.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()
    val error by viewModel.error.collectAsState()
    var onDimmisStatus by rememberSaveable() { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadNoteDetail(id)
    }

    val onRetry = {
        viewModel.loadNoteDetail(id)
    }

    val update = {
            id: String, title: String, content: String ->
        viewModel.updateNoteDetail(id, title, content)
    }

    val delete = {
            id: String ->
        viewModel.deleteNoteDetail(id)
    }

    val sheetStatus = {
            status: Boolean -> onDimmisStatus = status
    }

    Scaffold(
        topBar = { OrionTopBar(back) },
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
                    onRefresh = { viewModel.refreshingNoteDetail(id)},
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NoteDetailContent(innerPadding, note, sheetStatus, delete, back)

                    if(onDimmisStatus != false){
                        NoteUpdatateBottomSheet(sheetStatus, note, update)
                    }
                }
            }
        }
    }
}


@Composable
fun NoteDetailContent(
    padding: PaddingValues,
    note: NoteDetailResponseModel?,
    sheetStatus: (Boolean) -> Unit,
    delete: (String) -> Unit,
    back: () -> Unit
){
    LazyColumn(
        modifier = Modifier
            .padding(padding)
    ) {
        item{
            Text(text = note?.noteDetail?.title?: "")
        }

        item{
            Text(text = note?.noteDetail?.content?: "")
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
                    delete(note?.noteDetail?.id?: "")
                    back()
                }
            ) {
                Text(text = "Eliminar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteUpdatateBottomSheet(
    sheetStatus: (Boolean) -> Unit,
    note: NoteDetailResponseModel?,
    update: (String, String, String) -> Unit
){
    var title by rememberSaveable() { mutableStateOf(note?.noteDetail?.title?: "") }
    var content by rememberSaveable() { mutableStateOf(note?.noteDetail?.content?: "") }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { sheetStatus(false) },
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier.padding(paddingValues = PaddingValues())
        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = note?.noteDetail?.title?: "")}
                )
            }

            item {
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = note?.noteDetail?.content?: "")}
                )
            }

            item {
                Button(
                    onClick = {
                        update(note?.noteDetail?.id?: "", title, content)
                        sheetStatus(false)
                    }
                ) {
                    Text(text = "Actualizar")
                }
            }
        }
    }
}