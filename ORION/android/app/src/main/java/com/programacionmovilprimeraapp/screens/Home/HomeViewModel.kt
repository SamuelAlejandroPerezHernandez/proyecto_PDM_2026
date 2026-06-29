package com.programacionmovilprimeraapp.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.domain.NoteModel
import com.programacionmovilprimeraapp.domain.NoteRepository
import com.programacionmovilprimeraapp.domain.NoteRequestModel
import com.programacionmovilprimeraapp.domain.TaskRepository
import com.programacionmovilprimeraapp.domain.TaskRequestModel
import com.programacionmovilprimeraapp.orionnotes.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository: TaskRepository = TaskRepositoryImp(MyApp.sessionManager)
    private val noteRepository: NoteRepository = NoteRepositoryImp(MyApp.sessionManager)

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    var savingMessage = _savingMessage.asStateFlow()

    private val _notesList = MutableStateFlow<List<NoteModel>>(emptyList())
    val notesList = _notesList.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            noteRepository.getNotes()
                .onSuccess { list ->
                    _notesList.value = list
                }
                .onFailure { e ->
                    _savingMessage.value = "Error al cargar notas: ${e.message}"
                }
        }
    }

    fun InsertTask(CategoryId: String, Title: String, Description: String, DueDate: String) {
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
                    _savingMessage.value = "Tarea guardada con exito"
                }
                .onFailure { e ->
                    _savingMessage.value = "al ingresar la tarea ocurrio un error: ${e.message}"
                }
            _saving.value = false
        }
    }

    fun InsertNote(CategoryId: String, Title: String, Content: String) {
        if (Title.isBlank()) {
            _savingMessage.value = "El titulo es requerido"
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
                .onSuccess {
                    _savingMessage.value = "Nota guardada con éxito"
                    loadNotes() // MODIFICADO: Recarga las notas automáticamente al guardar una nueva
                }
                .onFailure { e ->
                    _savingMessage.value = "Al ingresar la nota ocurrió un error: ${e.message}"
                }
            _saving.value = false
        }
    }
}