package com.programacionmovilprimeraapp.features.note.domain.model

data class NoteModel(
    val id: String,
    val userId: String,
    val categoryId: String?,
    val title: String,
    val content: String
)