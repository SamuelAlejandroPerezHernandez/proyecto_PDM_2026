package com.programacionmovilprimeraapp.features.task.domain.repository

import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateRequestModel

interface TaskRepository {
    suspend fun createTask(request: TaskRequestModel): Result<TaskResponseModel>

    suspend fun getTask(): Result<List<TaskModel>>

    suspend fun getTaskDetail(id: String): Result<TaskDetailModel>

    suspend fun updateTask(id: String, request: TaskUpdateRequestModel): Result<Unit>

    suspend fun deleteTask(id: String): Result<Unit>
}