package com.programacionmovilprimeraapp.screens.TaskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.domain.TaskModel
import com.programacionmovilprimeraapp.domain.TaskRepository
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(): ViewModel(){

    private val repository: TaskRepository = TaskRepositoryImp(MyApp.sessionManager)

    private val _TaskList = MutableStateFlow<List<TaskModel>>(emptyList())
    val taskList = _TaskList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

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
                    println("🚨 ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
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
}