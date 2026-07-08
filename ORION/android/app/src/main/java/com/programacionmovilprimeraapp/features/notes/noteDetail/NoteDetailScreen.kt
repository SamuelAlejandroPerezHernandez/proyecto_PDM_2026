package com.programacionmovilprimeraapp.features.notes.noteDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.core.ui.components.OrionTopBar
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel
import com.programacionmovilprimeraapp.orionnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    id: String,
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
    goToNoteList: () -> Unit,
    back: () -> Unit,
    goToPerfil: () -> Unit
) {
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
    val update = { id: String, title: String, content: String ->
        viewModel.updateNoteDetail(id, title, content)
    }
    val delete = { id: String ->
        viewModel.deleteNoteDetail(id)
    }
    val sheetStatus = { status: Boolean -> onDimmisStatus = status }

    Scaffold(
        topBar = { OrionTopBar(back, goToPerfil) },
        bottomBar = { DetailBottomBar(goToHome, goToTaskList, goToNoteList) }
    ) { innerPadding ->
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
                    onRefresh = { viewModel.refreshingNoteDetail(id) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    NoteDetailContent(innerPadding, note, sheetStatus, delete, back)
                    if (onDimmisStatus != false) {
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
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notes,
                        contentDescription = null,
                        tint = colorResource(id = R.color.carbonBlack),
                        modifier = Modifier.width(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Título",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.carbonBlack)
                    )
                }
                Text(
                    text = note?.noteDetail?.title ?: "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.carbonBlack),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }

        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Description,
                        contentDescription = null,
                        tint = colorResource(id = R.color.carbonBlack),
                        modifier = Modifier.width(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Contenido",
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
                ) {
                    Text(
                        text = note?.noteDetail?.content ?: "",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white),
                        lineHeight = 24.sp
                    )
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
                    onClick = { sheetStatus(true) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.width(25.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Actualizar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
                OutlinedButton(
                    onClick = {
                        delete(note?.noteDetail?.id ?: "")
                        back()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.width(25.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Eliminar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
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
) {
    var title by rememberSaveable() { mutableStateOf(note?.noteDetail?.title ?: "") }
    var content by rememberSaveable() { mutableStateOf(note?.noteDetail?.content ?: "") }
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
                Text(
                    text = "Editar nota",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.carbonBlack)
                )
            }
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Título de la nota") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
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
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = "Contenido de la nota") },
                    minLines = 4,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.dustyGrape),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorResource(id = R.color.dustyGrape)
                    )
                )
            }
            item {
                Button(
                    onClick = {
                        update(note?.noteDetail?.id ?: "", title, content)
                        sheetStatus(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.dustyGrape)
                    )
                ) {
                    Text(
                        text = "Guardar cambios",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}