package com.programacionmovilprimeraapp.features.notes.noteList

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NoteAdd
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.programacionmovilprimeraapp.orionnotes.R


@Composable
fun NoteListBottomBar(
    goToHome: () -> Unit,
    goToTaskList: () -> Unit,
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
            selected = true,
            onClick = {  },
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