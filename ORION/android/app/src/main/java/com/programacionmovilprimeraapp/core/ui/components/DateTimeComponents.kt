package com.programacionmovilprimeraapp.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.programacionmovilprimeraapp.orionnotes.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDateSelector(
    calendarDate: (String) -> Unit
){
    var showCalendar by rememberSaveable() { mutableStateOf(false) }

    val CalendarState = rememberDatePickerState()

    Column{
        OutlinedButton(
            onClick = {showCalendar = true},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ){
            Row(

            ) {
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = null,
                    tint = colorResource(id = R.color.dustyGrape),
                    modifier = Modifier.size(18.dp)
                )

                Text(text = "Seleccionar fecha",
                    color = colorResource(id = R.color.carbonBlack)
                )
            }
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
                        },

                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.dustyGrape)
                        )
                    ){
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showCalendar = false},

                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.dustyGrape)
                        )
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
        OutlinedButton(
            onClick = {showClock = true},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(

            ) {
                Icon(
                    imageVector = Icons.Rounded.HourglassTop,
                    contentDescription = null,
                    tint = colorResource(id = R.color.dustyGrape),
                    modifier = Modifier.size(18.dp)
                )

                Text(text = "Seleccionar Hora",
                    color = colorResource(id = R.color.carbonBlack)
                )
            }


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
                        },

                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.dustyGrape)
                        )
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {showClock = false},

                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.dustyGrape)
                        )
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