package com.programacionmovilprimeraapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskResponseDto(
    val newTask: TaskDto
)

@Serializable
data class TaskDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    val title: String,
    val description: String,
    @SerialName("due_date") val dueDate: String,
    @SerialName("is_completed") val isCompleted: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("category_id") val categoryId: String
)
