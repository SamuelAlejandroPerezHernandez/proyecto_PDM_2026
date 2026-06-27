package com.programacionmovilprimeraapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
                    singleLine = true
                )
            }

            item{
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it},
                    label = { Text(text = " Ingrese La Descripcion De La Tarea") }
                )
            }

            item{
                DialogDateSelector(calendarDate)
            }

            item{
                DialogTimeSelector(ClockTime)
            }

            item{
                Button(
                    onClick = {
                        dueDate = "${dateT}${timeT}:00Z"
                        if(categoryId != null){
                            insertTask(categoryId, title, description, dueDate)
                        }
                        onDismiss()
                    }
                ) {
                    Text(text = "Guardar")
                }
            }

            item {
                saveMessage?.let {
                    Text(text = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDateSelector(
    calendarDate: (String) -> Unit
){
    var showCalendar by rememberSaveable() { mutableStateOf(false) }

    val CalendarState = rememberDatePickerState()

    Column{
        Button(onClick = {showCalendar = true}){
            Text(text = "Seleccionar fecha")
        }

        if(showCalendar){
            DatePickerDialog(
                onDismissRequest = {showCalendar = false},
                confirmButton = {
                    Button(
                        onClick = {
                            val milisegundos = CalendarState.selectedDateMillis

                            if(milisegundos != null){
                                val formateador = SimpleDateFormat("yyyy-MM-dd'T'", Locale.getDefault())
                                formateador.timeZone = TimeZone.getTimeZone("UTC")
                                calendarDate(formateador.format(Date(milisegundos)))
                            }

                            showCalendar = false
                        }
                    ){
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showCalendar = false}
                    ){
                        Text(text = "Cancelar")
                    }
                }

            ) {
                DatePicker(state = CalendarState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTimeSelector(
    ClockTime: (String) -> Unit
){
    var showClock by rememberSaveable() { mutableStateOf(false) }

    val ClockState = rememberTimePickerState(is24Hour = true)

    Column{
        Button(onClick = {showClock = true}) {
            Text(text = "Seleccionar Hora")
        }

        if(showClock){
            AlertDialog(
                onDismissRequest = {showClock = false},
                confirmButton = {
                    Button(
                        onClick = {
                            val hour = ClockState.hour
                            val minute = ClockState.minute
                            ClockTime("%02d:%02d".format(hour, minute))

                            showClock = false
                        }
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {showClock = false}
                    ) {
                        Text(text = "Cancelar")
                    }
                },

                text = {
                    TimePicker(state = ClockState)
                }
            )
        }
    }
}