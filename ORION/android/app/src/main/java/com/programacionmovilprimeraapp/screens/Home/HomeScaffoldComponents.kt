package com.programacionmovilprimeraapp.screens.Home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlaylistAddCheck
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.programacionmovilprimeraapp.orionnotes.R


@Composable
fun TaskFloatingButtom(
    CATEGORIA_TAREAS: String,
    CATEGORIA_NOTAS: String,
    categorySelected: (String) -> Unit
){

    var isOpen by rememberSaveable() { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = isOpen,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nueva Tarea",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    SmallFloatingActionButton(
                        onClick = {
                            isOpen = false
                            categorySelected(CATEGORIA_TAREAS)
                        },
                        containerColor = colorResource(id = R.color.pearlAqua),
                        contentColor = Color.Black,
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Task,
                            contentDescription = "boton para añadir tarea"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nueva Nota",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    SmallFloatingActionButton(
                        onClick = {
                            isOpen = false
                            categorySelected(CATEGORIA_NOTAS)
                        },
                        containerColor = colorResource(id = R.color.pearlAqua),
                        contentColor = Color.Black,
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Description,
                            contentDescription = "boton para añadir tarea"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                isOpen = true
            },
            contentColor = Color.Black,
            containerColor = colorResource(id = R.color.pearlAqua),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "boton para añadir tareas",
                modifier = Modifier
                    .size(30.dp)
            )
        }

    }
}

@Composable
fun HomeBottomBar(
    goToTaskScreen: () -> Unit
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
                onClick = { goToTaskScreen() },
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