package com.programacionmovilprimeraapp.features.task.data.mapper


import com.programacionmovilprimeraapp.features.task.data.dto.TaskDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskRequestDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseListDto
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseListModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel

fun TaskRequestModel.toTaskRequestDto(): TaskRequestDto{
    return TaskRequestDto(
        categoryId = categoryId,
        title = title,
        description = description,
        dueDate = dueDate
    )
}

fun TaskResponseDto.toTaskResponseModel(): TaskResponseModel{
    return TaskResponseModel(
        newTask = newTask.toTaskModel()
    )
}

fun TaskResponseListDto.toTaskResponseListModel(): TaskResponseListModel {
   return TaskResponseListModel(
       tasksList = tasksList.map { it.toTaskModel() }
   )
}

fun TaskDto.toTaskModel(): TaskModel{
    return TaskModel(
        id = id,
        userId = userId,
        title = title,
        description = description,
        dueDate = dueDate,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        categoryId = categoryId
    )
}