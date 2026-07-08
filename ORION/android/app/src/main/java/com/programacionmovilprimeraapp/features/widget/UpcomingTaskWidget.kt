package com.programacionmovilprimeraapp.features.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
private val ScreenBackground = ColorProvider(day = Color(0xFFF4F2F6), night = Color(0xFFF4F2F6))
private val CardBackground = ColorProvider(day = Color.White, night = Color.White)
private val TitleColor = ColorProvider(day = Color(0xFF1C1B1F), night = Color(0xFF1C1B1F))
private val DateAccentColor = ColorProvider(day = Color(0xFF1F7A43), night = Color(0xFF1F7A43))
private val DateAccentBackground = ColorProvider(day = Color(0xFFDCF3E3), night = Color(0xFFDCF3E3))
private val EmptyStateColor = ColorProvider(day = Color(0xFF79747E), night = Color(0xFF79747E))
private val ErrorColor = ColorProvider(day = Color(0xFFD32F2F), night = Color(0xFFD32F2F))

class UpcomingTasksWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = TaskRepositoryImp(MyApp.sessionManager)
        val result = repository.getUpcomingTasks()

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ScreenBackground)
                    .cornerRadius(20.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Tareas próximas",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = TitleColor,
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = GlanceModifier.height(12.dp))

                when {
                    result.isFailure -> {
                        Text(
                            text = "No se pudieron cargar las tareas",
                            style = TextStyle(color = ErrorColor, fontSize = 13.sp)
                        )
                    }
                    result.getOrNull().isNullOrEmpty() -> {
                        Text(
                            text = "No tienes tareas próximas",
                            style = TextStyle(color = EmptyStateColor, fontSize = 14.sp)
                        )
                    }
                    else -> {
                        val tasks = result.getOrNull().orEmpty()
                        LazyColumn(modifier = GlanceModifier.fillMaxWidth()) {
                            items(tasks) { task ->
                                Column {
                                    UpcomingTaskCard(task)
                                    Spacer(modifier = GlanceModifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun UpcomingTaskCard(task: TaskModel) {
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(CardBackground)
            .cornerRadius(14.dp)
            .padding(12.dp)
    ) {
        Text(
            text = task.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = TitleColor,
                fontSize = 15.sp
            )
        )
        Spacer(modifier = GlanceModifier.height(6.dp))
        Row {
            Box(
                modifier = GlanceModifier
                    .background(DateAccentBackground)
                    .cornerRadius(8.dp)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = task.dueDate.take(10),
                    style = TextStyle(color = DateAccentColor, fontSize = 12.sp)
                )
            }
        }
    }
}

class UpcomingTasksWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = UpcomingTasksWidget()
}