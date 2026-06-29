package com.programacionmovilprimeraapp.data.repository

import com.programacionmovilprimeraapp.data.dto.NoteResponseDto
import com.programacionmovilprimeraapp.data.dto.NotesListResponseDto
import com.programacionmovilprimeraapp.data.local.SessionManager
import com.programacionmovilprimeraapp.data.mapper.toNoteModel
import com.programacionmovilprimeraapp.data.mapper.toNoteRequestDto
import com.programacionmovilprimeraapp.data.remote.KtorClient
import com.programacionmovilprimeraapp.domain.NoteModel
import com.programacionmovilprimeraapp.domain.NoteRepository
import com.programacionmovilprimeraapp.domain.NoteRequestModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class NoteRepositoryImp(private val sessionManager: SessionManager) : NoteRepository {

    override suspend fun createNote(request: NoteRequestModel): Result<NoteModel> {
        return try {
            val token = sessionManager.getToken()
            val response: NoteResponseDto = KtorClient.client.post("/api/notes") {
                contentType(ContentType.Application.Json)
                setBody(request.toNoteRequestDto())
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()
            Result.success(response.toNoteModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNotes(): Result<List<NoteModel>> {
        return try {
            val token = sessionManager.getToken() ?: ""

            val response: NotesListResponseDto = KtorClient.client.get(urlString = "/api/notes") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()

            val notesModelList = response.notesList.map { dto ->
                NoteModel(
                    id = dto.id,
                    userId = dto.userId,
                    categoryId = dto.categoryId,
                    title = dto.title,
                    content = dto.content
                )
            }
            Result.success(notesModelList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}