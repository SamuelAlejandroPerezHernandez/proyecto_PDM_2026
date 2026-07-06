package com.programacionmovilprimeraapp.features.notes.domain.model

data class NotesRequestModel(
    val title: String,
    val content: String,
    val categoryId: String
)

data class NotesUpdateRequestModel(
    val title: String? = null,
    val content: String? = null,
    val categoryId: String? = null
)

data class NoteDetailResponseModel(
    val noteDetail: NoteModel
)

data class NotesResponseModel(
    val newNote: NoteModel
)

data class NoteModel(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val categoryId: String
)