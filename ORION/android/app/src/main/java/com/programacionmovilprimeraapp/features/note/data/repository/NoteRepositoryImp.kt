package com.programacionmovilprimeraapp.features.note.data.repository

import com.programacionmovilprimeraapp.core.data.local.SessionManager
import com.programacionmovilprimeraapp.core.data.remote.KtorClient
import com.programacionmovilprimeraapp.features.note.data.dto.NoteRequestDto
import com.programacionmovilprimeraapp.features.note.data.dto.NotesListResponseDto
import com.programacionmovilprimeraapp.features.note.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.note.domain.repository.NoteRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class NoteRepositoryImp(
    private val sessionManager: SessionManager
) : NoteRepository {

    override suspend fun getNotes(): Result<List<NoteModel>> {
        return try {
            val token = sessionManager.getToken() ?: ""
            val response: NotesListResponseDto = KtorClient.client.get(urlString = "/api/notes") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()
            val models = response.notesList.map { dto ->
                NoteModel(id = dto.id, userId = dto.userId, categoryId = dto.categoryId, title = dto.title, content = dto.content)
            }
            Result.success(models)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addNote(title: String, content: String, categoryId: String?): Result<Unit> {
        return try {
            val token = sessionManager.getToken() ?: ""
            val response = KtorClient.client.post(urlString = "/api/notes") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(NoteRequestDto(categoryId = categoryId, title = title, content = content))
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                Result.failure(Exception("Error del servidor (${response.status.value}): $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}