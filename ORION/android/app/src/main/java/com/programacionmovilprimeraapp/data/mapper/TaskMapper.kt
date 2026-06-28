package com.programacionmovilprimeraapp.data.mapper


import com.programacionmovilprimeraapp.data.dto.TaskDto
import com.programacionmovilprimeraapp.data.dto.TaskRequestDto
import com.programacionmovilprimeraapp.data.dto.TaskResponseDto
import com.programacionmovilprimeraapp.data.dto.TaskResponseListDto
import com.programacionmovilprimeraapp.domain.TaskModel
import com.programacionmovilprimeraapp.domain.TaskRequestModel
import com.programacionmovilprimeraapp.domain.TaskResponseListModel
import com.programacionmovilprimeraapp.domain.TaskResponseModel

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