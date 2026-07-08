package com.programacionmovilprimeraapp.features.widget

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpcomingTasksWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = TaskRepositoryImp(MyApp.sessionManager)

        provideContent {
            var resultState by remember { mutableStateOf<Result<List<TaskModel>>?>(null) }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    resultState = repository.getUpcomingTasks()
                }
            }

            val backgroundColor = ColorProvider(day = Color(0xFF1E293B), night = Color(0xFF1E293B))
            val textColor = ColorProvider(day = Color.White, night = Color.White)

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Tareas próximas",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        fontSize = 16.sp
                    ),
                    modifier = GlanceModifier.padding(bottom = 4.dp)
                )

                val currentResult = resultState
                when {
                    currentResult == null -> {
                        Text(
                            text = "Cargando tareas...",
                            style = TextStyle(color = textColor)
                        )
                    }
                    currentResult.isFailure -> {
                        val exception = currentResult.exceptionOrNull()
                        Text(
                            text = "Error: ${exception?.localizedMessage ?: "Error de red"}",
                            style = TextStyle(color = textColor)
                        )
                    }
                    else -> {
                        val tasks = currentResult.getOrNull()
                        if (tasks.isNullOrEmpty()) {
                            Text(
                                text = "No tienes tareas próximas",
                                style = TextStyle(color = textColor)
                            )
                        } else {
                            tasks.forEach { task ->
                                Text(
                                    text = "• ${task.title} — ${task.dueDate.take(10)}",
                                    style = TextStyle(color = textColor),
                                    modifier = GlanceModifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

class UpcomingTasksWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = UpcomingTasksWidget()
}