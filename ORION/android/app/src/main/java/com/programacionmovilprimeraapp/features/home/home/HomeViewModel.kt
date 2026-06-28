package com.programacionmovilprimeraapp.features.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val repository: TaskRepository = TaskRepositoryImp(MyApp.Companion.sessionManager)

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    var savingMessage = _savingMessage.asStateFlow()

    fun InsertTask(CategoryId: String, Title: String, Description: String, DueDate: String){
        _saving.value = true

        val taskRequest = TaskRequestModel(
            categoryId = CategoryId,
            title = Title,
            description = Description,
            dueDate = DueDate
        )

        viewModelScope.launch {
            repository.createTask(taskRequest)
                .onSuccess {
                        response ->
                    _savingMessage.value = "Tarea guardad con exito"
                }
                .onFailure {
                        e ->
                    _savingMessage.value = "al ingresar la tarea ocurrio un error: ${e.message}"
                }

            _saving.value = false
        }

    }

}