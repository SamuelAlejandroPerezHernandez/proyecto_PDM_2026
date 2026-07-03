package com.programacionmovilprimeraapp.features.note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.features.note.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.features.note.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.note.domain.repository.NoteRepository
import com.programacionmovilprimeraapp.core.data.local.SessionManager
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    val savingMessage = _savingMessage.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess = _saveSuccess.asStateFlow()

    private val _deleteMessage = MutableStateFlow<String?>(null)
    val deleteMessage = _deleteMessage.asStateFlow()

    // Nuevo: mensaje y señal de éxito para editar
    private val _editMessage = MutableStateFlow<String?>(null)
    val editMessage = _editMessage.asStateFlow()

    private val _editSuccess = MutableStateFlow(false)
    val editSuccess = _editSuccess.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getNotes()
                .onSuccess { list -> _notes.value = list }
                .onFailure { /* Manejar error */ }
            _isLoading.value = false
        }
    }

    fun createNote(title: String, content: String, categoryId: String? = null) {
        viewModelScope.launch {
            repository.addNote(title, content, categoryId)
                .onSuccess {
                    _savingMessage.value = null
                    _saveSuccess.value = true
                    loadNotes()
                }
                .onFailure { e ->
                    android.util.Log.e("NoteViewModel", "ERROR AL GUARDAR NOTA", e)
                    _savingMessage.value = "Error al guardar la nota: ${e.message}"
                }
        }
    }

    fun updateNote(id: String, title: String, content: String, categoryId: String?) {
        viewModelScope.launch {
            repository.updateNote(id, title, content, categoryId)
                .onSuccess {
                    _editMessage.value = null
                    _editSuccess.value = true
                    loadNotes()
                }
                .onFailure { e ->
                    android.util.Log.e("NoteViewModel", "ERROR AL EDITAR NOTA", e)
                    _editMessage.value = "Error al editar la nota: ${e.message}"
                }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            repository.deleteNote(id)
                .onSuccess {
                    _deleteMessage.value = null
                    loadNotes()
                }
                .onFailure { e ->
                    android.util.Log.e("NoteViewModel", "ERROR AL ELIMINAR NOTA", e)
                    _deleteMessage.value = "Error al eliminar la nota: ${e.message}"
                }
        }
    }

    fun clearSavingMessage() {
        _savingMessage.value = null
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }

    fun clearEditMessage() {
        _editMessage.value = null
    }

    fun resetEditSuccess() {
        _editSuccess.value = false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val sessionManager = SessionManager.getInstance(application)
                val repository = NoteRepositoryImp(sessionManager)

                return NoteViewModel(repository) as T
            }
        }
    }
}