package com.programacionmovilprimeraapp.features.notes.domain.repository

import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesUpdateRequestModel

interface NoteRepositoryN{
    suspend fun createNote(request: NotesRequestModel): Result<NotesResponseModel>

    suspend fun getNote(): Result<List<NoteModel>>

    suspend fun getNoteDetail(id: String): Result<NoteDetailResponseModel>

    suspend fun updateNote(id: String, request: NotesUpdateRequestModel): Result<Unit>

    suspend fun deleteNote(id: String): Result<Unit>
}