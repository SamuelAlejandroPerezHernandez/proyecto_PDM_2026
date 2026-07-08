package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NoteAdd
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.programacionmovilprimeraapp.orionnotes.R

@Composable
fun DetailBottomBar(
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
    goToNoteList: () -> Unit
){
    NavigationBar(
        containerColor = colorResource(id = R.color.carbonBlack),
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { goToHome() },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.pearlAqua),
                unselectedIconColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { goToTaskList() },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Task,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.pearlAqua),
                unselectedIconColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { goToNoteList() },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.NoteAdd,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.pearlAqua),
                unselectedIconColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrionTopBar(
    back: () -> Unit
){
    TopAppBar(
        title = {},

        navigationIcon = {
            IconButton(
                onClick = { back() },
                modifier = Modifier
                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },

        actions = {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource( id = R.color.carbonBlack )
        )
    )
}