package com.programacionmovilprimeraapp.features.task.taskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.task.domain.model.TaskUpdateRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskListViewModel(): ViewModel(){

    private val repository: TaskRepository = TaskRepositoryImp(MyApp.Companion.sessionManager)

    private val _TaskList = MutableStateFlow<List<TaskModel>>(emptyList())
    val taskList = _TaskList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    var savingMessage = _savingMessage.asStateFlow()

    fun loadTask(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getTask()
                .onSuccess {
                    tasks ->
                    _TaskList.value = tasks
                }
                .onFailure {
                    e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _loading.value = false
        }
    }

    fun refreshingTasks(){
        viewModelScope.launch {
            _refreshing.value = true
            _error.value = null

            repository.getTask()
                .onSuccess {
                        tasks ->
                    _TaskList.value = tasks
                }
                .onFailure {
                        e ->
                    _error.value = e.message
                }

            _refreshing.value = false
        }
    }

    fun updateTaskStatus(id: String, isCompleted: Boolean){
        _saving.value = true

        _TaskList.value = _TaskList.value.map {
                task ->
            if(task.id == id){
                task.copy(isCompleted = isCompleted)
            }
            else {
                task
            }
        }

        val updateRequest = TaskUpdateRequestModel(
            isCompleted = isCompleted
        )

        viewModelScope.launch {
            repository.updateTask(id, updateRequest)
                .onSuccess {
                    _savingMessage.value = "FELICIDADES COMPLETASTE LA TAREA"
                }
                .onFailure {
                    _savingMessage.value = "parece que hubo un error al intentar marcara la tarea como completa"

                    _TaskList.value = _TaskList.value.map {
                            task ->
                        if(task.id == id){
                            task.copy(isCompleted = isCompleted)
                        }
                        else {
                            task
                        }
                    }
                }

            _saving.value = false
        }
    }
}