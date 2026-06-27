package com.programacionmovilprimeraapp.data.mapper

import com.programacionmovilprimeraapp.data.dto.NoteRequestDto
import com.programacionmovilprimeraapp.data.dto.NoteResponseDto
import com.programacionmovilprimeraapp.domain.NoteRequestModel
import com.programacionmovilprimeraapp.domain.NoteModel

fun NoteRequestModel.toNoteRequestDto() = NoteRequestDto(
    categoryId = this.categoryId,
    title = this.title,
    content = this.content
)

fun NoteResponseDto.toNoteModel() = NoteModel(
    id = this.newNote.id,
    userId = this.newNote.userId,
    categoryId = this.newNote.categoryId,
    title = this.newNote.title,
    content = this.newNote.content
)