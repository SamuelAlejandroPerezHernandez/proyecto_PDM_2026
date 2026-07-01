package com.programacionmovilprimeraapp.features.note.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequestDto(
    @SerialName("category_id") val categoryId: String?,
    val title: String,
    val content: String
)

@Serializable
data class NoteDetailDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("category_id") val categoryId: String?,
    val title: String,
    val content: String
)

@Serializable
data class NotesListResponseDto(
    val notesList: List<NoteDetailDto>
)