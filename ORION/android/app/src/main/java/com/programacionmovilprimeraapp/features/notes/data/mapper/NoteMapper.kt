package com.programacionmovilprimeraapp.features.notes.data.mapper

import com.programacionmovilprimeraapp.features.notes.data.dto.NoteDetailResponseDto
import com.programacionmovilprimeraapp.features.notes.data.dto.NotesRequestDto
import com.programacionmovilprimeraapp.features.notes.data.dto.NotesResponseDto
import com.programacionmovilprimeraapp.features.notes.data.dto.UpdateNotesRequestDto
import com.programacionmovilprimeraapp.features.notes.data.dto.NoteDto
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesUpdateRequestModel

fun NotesRequestModel.toNotesRequestDto(): NotesRequestDto {
    return NotesRequestDto(
        title = title,
        content = content,
        categoryId = categoryId
    )
}

fun NotesUpdateRequestModel.toUpdateNotesRequestDto(): UpdateNotesRequestDto {
    return UpdateNotesRequestDto(
        title = title,
        content = content,
        categoryId = categoryId
    )
}

fun NoteDto.toNoteModel(): NoteModel{
    return NoteModel(
        id = id,
        userId = userId,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        categoryId = categoryId
    )
}

fun NotesResponseDto.toNotesResponseModel(): NotesResponseModel {
    return NotesResponseModel(
        newNote = newNote.toNoteModel()
    )
}

fun NoteDetailResponseDto.toNoteDetailResponseModel(): NoteDetailResponseModel{
    return  NoteDetailResponseModel(
        noteDetail = noteDetail.toNoteModel()
    )
}