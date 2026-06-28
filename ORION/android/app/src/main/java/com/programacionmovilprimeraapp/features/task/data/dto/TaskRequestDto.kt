package com.programacionmovilprimeraapp.features.task.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskRequestDto(
    @SerialName("category_id") val categoryId: String,
    val title: String,
    val description: String,
    @SerialName("due_date") val dueDate: String
)