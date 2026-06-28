package com.programacionmovilprimeraapp.domain

interface TaskRepository {
    suspend fun createTask(request: TaskRequestModel): Result<TaskResponseModel>

    suspend fun getTask(): Result<List<TaskModel>>
}