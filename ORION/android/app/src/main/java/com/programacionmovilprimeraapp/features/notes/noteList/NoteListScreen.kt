package com.programacionmovilprimeraapp.features.notes.noteList

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacionmovilprimeraapp.core.ui.components.ErrorContent
import com.programacionmovilprimeraapp.core.ui.components.LoadingContent
import com.programacionmovilprimeraapp.core.ui.components.OrionTopBar
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.orionnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    goToDetailNote: (String) -> Unit,
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
    back: () -> Unit
){
    val viewModel: NoteListViewModel = viewModel()
    val NoteList by viewModel.noteList.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNote()
    }

    val onRetry = {
        viewModel.loadNote()
    }

    Scaffold(
        topBar = { OrionTopBar(back) },
        bottomBar = { NoteListBottomBar(goToHome, goToTaskList) }
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
                    onRefresh = { viewModel.refreshingNote() },
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NoteListContent(NoteList, innerPadding, goToDetailNote)
                }
            }
        }
    }
}


@Composable
fun NoteListContent(
    NoteList: List<NoteModel>,
    padding: PaddingValues,
    goToDetailNote: (String) -> Unit,
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(NoteList){
            note ->

            Card(
                onClick = { goToDetailNote(note.id) },
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



                    Text(text = note.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                }
            }

        }
    }
}
