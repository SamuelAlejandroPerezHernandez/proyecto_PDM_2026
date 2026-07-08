package com.programacionmovilprimeraapp.features.task.domain.model

data class TaskRequestModel(
    val categoryId: String,
    val title: String,
    val description: String,
    val dueDate: String
)

data class TaskUpdateRequestModel(
    val categoryId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val dueDate: String? = null,
    val isCompleted: Boolean
)

data class TaskResponseModel(
    val newTask: TaskModel
)

data class TaskDetailModel(
    val taskDetail: TaskModel
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
    val categoryId: String,
)

