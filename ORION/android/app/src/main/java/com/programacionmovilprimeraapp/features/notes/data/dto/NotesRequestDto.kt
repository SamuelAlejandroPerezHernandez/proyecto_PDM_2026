package com.programacionmovilprimeraapp.features.notes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesRequestDto(
    val title: String,
    val content: String,
    @SerialName("category_id") val categoryId: String
)

@Serializable
data class UpdateNotesRequestDto(
    val title: String?,
    val content: String?,
    @SerialName("category_id") val categoryId: String?
)