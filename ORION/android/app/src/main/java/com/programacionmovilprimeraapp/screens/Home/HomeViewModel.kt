package com.programacionmovilprimeraapp.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.domain.NoteRequestModel
import com.programacionmovilprimeraapp.domain.NoteRepository
import com.programacionmovilprimeraapp.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.domain.TaskRepository
import com.programacionmovilprimeraapp.domain.TaskRequestModel
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val repository: TaskRepository = TaskRepositoryImp(MyApp.sessionManager)
    private val noteRepository: NoteRepository = NoteRepositoryImp(MyApp.sessionManager)

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
                .onSuccess { response ->
                    _savingMessage.value = "Tarea guardad con exito"
                }
                .onFailure { e ->
                    _savingMessage.value = "al ingresar la tarea ocurrio un error: ${e.message}"
                }

            _saving.value = false
        }
    }
    fun InsertNote(CategoryId: String, Title: String, Content: String) {
        if (Title.isBlank()) {
            _savingMessage.value = "El título es requerido"
            return
        }

        _saving.value = true
        _savingMessage.value = null

        val noteRequest = NoteRequestModel(
            categoryId = CategoryId,
            title = Title,
            content = Content
        )

        viewModelScope.launch {
            noteRepository.createNote(noteRequest)
                .onSuccess { response ->
                    _savingMessage.value = "Nota guardada con éxito"
                }
                .onFailure { e ->
                    _savingMessage.value = "Al ingresar la nota ocurrió un error: ${e.message}"
                }
            _saving.value = false
        }
    }
}