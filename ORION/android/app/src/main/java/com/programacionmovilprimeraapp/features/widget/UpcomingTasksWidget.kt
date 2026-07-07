package com.programacionmovilprimeraapp.features.widget

import android.content.Context
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

class UpcomingTasksWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = TaskRepositoryImp(MyApp.sessionManager)
        val result = repository.getUpcomingTasks()

        provideContent {
            val tasks = result.getOrNull()

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
                    )
                )

                when {
                    result.isFailure -> {
                        Text(
                            text = "Error: ${result.exceptionOrNull()?.message ?: "desconocido"}",
                            style = TextStyle(color = textColor)
                        )
                    }
                    tasks.isNullOrEmpty() -> {
                        Text(
                            text = "No tienes tareas próximas",
                            style = TextStyle(color = textColor)
                        )
                    }
                    else -> {
                        tasks.forEach { task ->
                            Text(
                                text = "• ${task.title} — ${task.dueDate.take(10)}",
                                style = TextStyle(color = textColor)
                            )
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