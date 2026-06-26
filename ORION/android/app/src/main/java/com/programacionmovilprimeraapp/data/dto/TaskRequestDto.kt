package com.programacionmovilprimeraapp.data.dto

import io.ktor.util.collections.StringMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskRequestDto(
    @SerialName("category_id") val categoryId: String,
    val title: String,
    val description: String,
    @SerialName("due_date") val dueDate: String,
    @SerialName("due_time") val dueTime: String
)