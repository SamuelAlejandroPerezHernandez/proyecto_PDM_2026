package com.programacionmovilprimeraapp.features.notes.data.repository

import com.programacionmovilprimeraapp.core.data.local.SessionManager
import com.programacionmovilprimeraapp.core.data.remote.KtorClient
import com.programacionmovilprimeraapp.features.notes.data.dto.NoteDetailResponseDto
import com.programacionmovilprimeraapp.features.notes.data.dto.NoteResponseListDto
import com.programacionmovilprimeraapp.features.notes.data.dto.NotesResponseDto
import com.programacionmovilprimeraapp.features.notes.data.mapper.toNoteDetailResponseModel
import com.programacionmovilprimeraapp.features.notes.data.mapper.toNoteModel
import com.programacionmovilprimeraapp.features.notes.data.mapper.toNotesRequestDto
import com.programacionmovilprimeraapp.features.notes.data.mapper.toNotesResponseModel
import com.programacionmovilprimeraapp.features.notes.data.mapper.toUpdateNotesRequestDto
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesUpdateRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.repository.NoteRepositoryN
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class NoteRepositoryImp(private val sessionManager: SessionManager): NoteRepositoryN{
    override suspend fun createNote(request: NotesRequestModel): Result<NotesResponseModel> {
        return try{
            val request = request.toNotesRequestDto()
            val token = sessionManager.getToken()

            val response: NotesResponseDto = KtorClient.client
                .post("/api/notes/postNotes"){
                    contentType(ContentType.Application.Json)
                    setBody(request)

                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()

            Result.success(
                response.toNotesResponseModel()
            )
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getNote(): Result<List<NoteModel>> {
        return try {
            val token = sessionManager.getToken()

            val response: NoteResponseListDto = KtorClient.client
                .get("/api/notes/getNotes"){
                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()

            Result.success(
                response.notesList.map { it.toNoteModel() }
            )
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getNoteDetail(id: String): Result<NoteDetailResponseModel> {
        return try {
            val token = sessionManager.getToken()

            val response: NoteDetailResponseDto = KtorClient.client
                .get("/api/notes/detail/${id}"){
                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()

            Result.success(
                response.toNoteDetailResponseModel()
            )
        }
        catch (e: Exception){
            Result.failure(e)
        }

    }

    override suspend fun updateNote(id: String, request: NotesUpdateRequestModel): Result<Unit> {
        return try{
            val request = request.toUpdateNotesRequestDto()
            val token = sessionManager.getToken()

            KtorClient.client.put("/api/notes/update/${id}"){
                    contentType(ContentType.Application.Json)
                    setBody(request)

                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }

            Result.success(Unit)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(id: String): Result<Unit> {
        return try{
            val token = sessionManager.getToken()

            KtorClient.client.delete("/api/notes/delete/${id}"){
                header(HttpHeaders.Authorization, "Bearer ${token}")
            }

            Result.success(Unit)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}