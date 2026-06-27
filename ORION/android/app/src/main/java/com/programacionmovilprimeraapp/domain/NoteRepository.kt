package com.programacionmovilprimeraapp.domain

interface NoteRepository {
    suspend fun createNote(request: NoteRequestModel): Result<NoteModel>
}