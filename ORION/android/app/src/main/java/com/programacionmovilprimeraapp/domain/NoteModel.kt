package com.programacionmovilprimeraapp.domain

data class NoteRequestModel(
    val categoryId: String,
    val title: String,
    val content: String
)

data class NoteResponseModel(
    val id: String,
    val userId: String,
    val categoryId: String,
    val title: String,
    val content: String
)