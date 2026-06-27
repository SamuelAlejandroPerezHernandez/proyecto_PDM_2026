package com.programacionmovilprimeraapp.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.local.SessionManager
import com.programacionmovilprimeraapp.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.domain.TaskRepository
import com.programacionmovilprimeraapp.domain.TaskRequestModel
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val repository: TaskRepository = TaskRepositoryImp(MyApp.sessionManager)

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