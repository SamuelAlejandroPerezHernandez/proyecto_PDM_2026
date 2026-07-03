package com.programacionmovilprimeraapp.features.task.data.mapper


import com.programacionmovilprimeraapp.features.task.data.dto.TaskDeleteDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskDetailDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskRequestDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskResponseListDto
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseListModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.features.task.data.dto.TaskUpdateDto
import com.programacionmovilprimeraapp.features.task.data.dto.TaskUpdateRequestDto
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDeleteModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateRequestModel

fun TaskRequestModel.toTaskRequestDto(): TaskRequestDto{
    return TaskRequestDto(
        categoryId = categoryId,
        title = title,
        description = description,
        dueDate = dueDate
    )
}

fun TaskUpdateRequestModel.toTaskUpdateRequestDto(): TaskUpdateRequestDto {
    return TaskUpdateRequestDto(
        categoryId = categoryId,
        title = title,
        description = description,
        dueDate = dueDate,
        isCompleted = isCompleted
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

fun TaskDetailDto.toTaskDetailModel(): TaskDetailModel{
    return TaskDetailModel(
        taskDetail = taskDetail.toTaskModel()
    )
}

fun TaskUpdateDto.toTaskUpdateModel(): TaskUpdateModel{
    return TaskUpdateModel(
        updateTask = updateTask.toTaskModel()
    )
}

fun TaskDeleteDto.toTaskDeleteModel(): TaskDeleteModel{
    return TaskDeleteModel(
        deleteTask = deleteTask.toTaskModel()
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