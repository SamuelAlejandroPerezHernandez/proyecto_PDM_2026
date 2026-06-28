package com.programacionmovilprimeraapp.domain

import com.programacionmovilprimeraapp.data.dto.TaskDto
import kotlinx.serialization.SerialName

data class TaskRequestModel(
    val categoryId: String,
    val title: String,
    val description: String,
    val dueDate: String
)

data class TaskResponseModel(
    val newTask: TaskModel
)

data class TaskResponseListModel(
    val tasksList: List<TaskModel>
)

data class TaskModel(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val categoryId: String
)

