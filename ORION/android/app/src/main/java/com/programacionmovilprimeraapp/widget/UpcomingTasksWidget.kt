package com.programacionmovilprimeraapp.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import androidx.compose.ui.unit.dp

class UpcomingTasksWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = TaskRepositoryImp(MyApp.sessionManager)
        val tasks: List<TaskModel> = repository.getUpcomingTasks()
            .getOrElse { emptyList() }

        provideContent {
            GlanceTheme {
                WidgetContent(tasks)
            }
        }
    }
}

@Composable
private fun WidgetContent(tasks: List<TaskModel>) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(12.dp)
    ) {
        Text(
            text = "Próximas tareas",
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )

        if (tasks.isEmpty()) {
            Text(
                text = "No hay tareas próximas",
                style = TextStyle(
                    color = ColorProvider(Color.LightGray),
                    fontSize = 13.sp
                )
            )
        } else {
            tasks.forEach { task ->
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = task.title,
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = task.dueDate,
                        style = TextStyle(
                            color = ColorProvider(Color(0xFF4DD0C8)),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}