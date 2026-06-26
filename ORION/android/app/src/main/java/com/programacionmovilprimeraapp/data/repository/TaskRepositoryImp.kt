package com.programacionmovilprimeraapp.data.repository


import com.programacionmovilprimeraapp.data.dto.TaskResponseDto
import com.programacionmovilprimeraapp.data.local.SessionManager
import com.programacionmovilprimeraapp.data.mapper.toTaskRequestDto
import com.programacionmovilprimeraapp.data.mapper.toTaskResponseModel
import com.programacionmovilprimeraapp.data.remote.KtorClient
import com.programacionmovilprimeraapp.domain.TaskRepository
import com.programacionmovilprimeraapp.domain.TaskRequestModel
import com.programacionmovilprimeraapp.domain.TaskResponseModel
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class TaskRepositoryImp(private val sessionManager: SessionManager): TaskRepository{
    override suspend fun createTask(request: TaskRequestModel): Result<TaskResponseModel> {
        return try {
            val request = request.toTaskRequestDto()

            val token = sessionManager.getToken()

            val response: TaskResponseDto = KtorClient.client
                .post("/api/tasks/postTask"){
                    contentType(ContentType.Application.Json)
                    setBody(request)

                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()

            Result.success(
                response.toTaskResponseModel()
            )
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}