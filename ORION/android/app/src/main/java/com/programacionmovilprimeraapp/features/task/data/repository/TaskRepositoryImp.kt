package com.programacionmovilprimeraapp.features.task.data.repository

import com.programacionmovilprimeraapp.core.data.local.SessionManager
import com.programacionmovilprimeraapp.core.data.remote.KtorClient
import com.programacionmovilprimeraapp.features.task.data.dto.TaskDetailDto
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseListDto
import com.programacionmovilprimeraapp.features.task.data.dto.UpcomingTasksResponseDto
import com.programacionmovilprimeraapp.features.task.data.mapper.toTaskDetailModel
import com.programacionmovilprimeraapp.features.task.data.mapper.toTaskModel
import com.programacionmovilprimeraapp.features.task.data.mapper.toTaskRequestDto
import com.programacionmovilprimeraapp.features.task.data.mapper.toTaskResponseModel
import com.programacionmovilprimeraapp.features.task.data.mapper.toTaskUpdateRequestDto
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateRequestModel
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

class TaskRepositoryImp(private val sessionManager: SessionManager): TaskRepository {
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

    override suspend fun getTask(): Result<List<TaskModel>> {
        return try{
            val token = sessionManager.getToken()

            val response: TaskResponseListDto = KtorClient.client
                .get("/api/tasks/getTask"){
                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()

            Result.success(
                response.tasksList.map { it.toTaskModel() }
            )
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getTaskDetail(id: String): Result<TaskDetailModel> {
       return try{
           val token = sessionManager.getToken()

           val response: TaskDetailDto = KtorClient.client
               .get("/api/tasks/detail/${id}"){
                   header(HttpHeaders.Authorization, "Bearer ${token}")
               }
               .body()

           Result.success(
               response.toTaskDetailModel()
           )
       }
       catch (e: Exception){
           Result.failure(e)
       }
    }

    override suspend fun updateTask(id: String, request: TaskUpdateRequestModel): Result<Unit> {
        return try{
            val request = request.toTaskUpdateRequestDto()

            val token = sessionManager.getToken()


            KtorClient.client.put("/api/tasks/update/${id}") {
                contentType(ContentType.Application.Json)
                setBody(request)

                header(HttpHeaders.Authorization, "Bearer ${token}")

            }

            Result.success(Unit)
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return try{
            val token = sessionManager.getToken()


            KtorClient.client.delete("/api/tasks/delete/${id}"){
                header(HttpHeaders.Authorization, "Bearer ${token}")
            }

            Result.success(Unit)
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingTasks(): Result<List<TaskModel>> {
        return try {
            val token = sessionManager.getToken()

            val response: UpcomingTasksResponseDto = KtorClient.client
                .get("/api/tasks/upcoming") {
                    header(HttpHeaders.Authorization, "Bearer ${token}")
                }
                .body()
            Result.success(
                response.upcomingTasks.map { it.toTaskModel() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}