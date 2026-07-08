package com.programacionmovilprimeraapp.features.notes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesResponseDto(
    val newNote: NoteDto
)

@Serializable
data class NoteResponseListDto(
    val notesList: List<NoteDto>
)

@Serializable
data class NoteDetailResponseDto(
    val noteDetail: NoteDto
)

@Serializable
data class NoteDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("update_at") val updatedAt: String,
    @SerialName("category_id") val categoryId: String
)

@Serializable
data class RecentNotesResponseDto(
    val recentNotes: List<NoteDto>
)