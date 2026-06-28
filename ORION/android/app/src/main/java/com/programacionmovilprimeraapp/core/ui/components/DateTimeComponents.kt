package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
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