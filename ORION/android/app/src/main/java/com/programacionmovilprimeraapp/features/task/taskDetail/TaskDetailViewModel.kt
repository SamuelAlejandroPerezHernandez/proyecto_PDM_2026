package com.programacionmovilprimeraapp.features.task.taskDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateRequestModel
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(): ViewModel(){
    private val repository: TaskRepository = TaskRepositoryImp(MyApp.sessionManager)

    private val _responseTaskModel = MutableStateFlow<TaskDetailModel?>(null)
    val responseTaskModel = _responseTaskModel.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _processing = MutableStateFlow(false)
    val processing = _processing.asStateFlow()

    private val _processingMessage = MutableStateFlow<String?>(null)
    var processingMessage = _processingMessage.asStateFlow()

    fun loadTaskDetail(id: String){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getTaskDetail(id)
                .onSuccess {
                    response ->
                    _responseTaskModel.value = response
                }
                .onFailure {
                    e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _loading.value = false
        }
    }

    fun refreshingTaskDetail(id: String){
        viewModelScope.launch {
            _refreshing.value = true
            _error.value = null

            repository.getTaskDetail(id)
                .onSuccess {
                        response ->
                    _responseTaskModel.value = response
                }
                .onFailure {
                        e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _refreshing.value = false
        }
    }

    fun updateTaskDetail(id: String, Title: String, Description: String, DueDate: String){
        _processing.value = true

        val update = TaskUpdateRequestModel(
            title = Title,
            description = Description,
            dueDate = DueDate,
            isCompleted = false
        )

        viewModelScope.launch {
            repository.updateTask(id, update)
                .onSuccess {
                    _processingMessage.value = "la tarea se actualizo correctamente"
                    refreshingTaskDetail(id)
                }
                .onFailure {
                    _processingMessage.value = "hubo un error al intentar actualizar la tarea"
                }

            _processing.value = false
        }
    }

    fun deleteTaskDetail(id: String){
        _processing.value = true

        viewModelScope.launch {
            repository.deleteTask(id)
                .onSuccess {
                    _processingMessage.value = "Se elimino la tarea de manera exitosa"
                }
                .onFailure {
                    _processingMessage.value = "hubo un problema al intentar eliminar la tarea"
                }

            _processing.value = false
        }
    }

}