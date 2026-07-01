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

    fun clearSavingMessage() {
        _savingMessage.value = null
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
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