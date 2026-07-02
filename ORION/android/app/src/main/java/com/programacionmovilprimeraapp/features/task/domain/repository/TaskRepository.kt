package com.programacionmovilprimeraapp.features.task.domain.repository

import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel

interface TaskRepository {
    suspend fun createTask(request: TaskRequestModel): Result<TaskResponseModel>

    suspend fun getTask(): Result<List<TaskModel>>


    suspend fun getUpcomingTasks(): Result<List<TaskModel>>
}