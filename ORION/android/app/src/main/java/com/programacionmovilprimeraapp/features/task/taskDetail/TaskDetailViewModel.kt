package com.programacionmovilprimeraapp.features.task.taskDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskDetailModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskResponseModel
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

    fun updateTaskDetail(id: String, CategoryId: String, Title: String, Description: String, DueDate: String){

    }
}