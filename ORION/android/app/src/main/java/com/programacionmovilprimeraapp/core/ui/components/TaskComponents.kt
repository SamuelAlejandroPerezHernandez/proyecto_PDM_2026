package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskBottomSheet(
    insertTask: (String, String, String, String) -> Unit,
    categoryId: String?,
    onDismiss: () -> Unit,
    saveMessage: String?
){
    var title by rememberSaveable() { mutableStateOf("") }
    var description by rememberSaveable() { mutableStateOf("") }

    var dueDate by rememberSaveable() { mutableStateOf("") }

    var dateT by rememberSaveable() { mutableStateOf("") }
    var timeT by rememberSaveable() { mutableStateOf("") }

    val calendarDate = {
            date: String -> dateT = date
    }

    val ClockTime = {
            time: String -> timeT = time
    }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {onDismiss()},
        sheetState = sheetState
    ) {
        LazyColumn(

        ){
            item{
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = { Text(text = " Ingrese El TItulo De La Tarea") },
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

            item{
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it},
                    label = { Text(text = " Ingrese La Descripcion De La Tarea") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.dustyGrape),
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = colorResource(id = R.color.dustyGrape))
                )
            }

            item{
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        DialogDateSelector(calendarDate)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        DialogTimeSelector(ClockTime)
                    }
                }
            }

            item{
                Button(
                    onClick = {
                        dueDate = "${dateT}T${timeT}:00Z"
                        if(categoryId != null){
                            insertTask(categoryId, title, description, dueDate)
                        }
                        onDismiss()
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
                    Text(text = "Guardar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBottomSheet(
    insertNote: (String, String, String) -> Unit,
    categoryId: String?,
    onDismiss: () -> Unit,
    saveMessage: String?
){
    var title by rememberSaveable() { mutableStateOf("") }
    var content by rememberSaveable() { mutableStateOf("") }



    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {onDismiss()},
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            item{
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = { Text(text = " Ingrese El TItulo De La Tarea") },
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

            item{
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it},
                    label = { Text(text = " Ingrese La Descripcion De La Tarea") },
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

            item{
                Button(
                    onClick = {
                        if(categoryId != null){
                            insertNote(categoryId, title, content)
                        }

                        onDismiss()
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
                    Text(text = "Guardar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 4.dp))
                }
            }
        }
    }
}