package com.programacionmovilprimeraapp.features.notes.noteDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.notes.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteDetailResponseModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesUpdateRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.repository.NoteRepositoryN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(): ViewModel(){
    private val repository: NoteRepositoryN = NoteRepositoryImp(MyApp.sessionManager)

    private val _responseNoteModel = MutableStateFlow<NoteDetailResponseModel?>(null)
    val responseNoteModel = _responseNoteModel.asStateFlow()

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

    fun loadNoteDetail(id: String){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getNoteDetail(id)
                .onSuccess {
                        response ->
                    _responseNoteModel.value = response
                }
                .onFailure {
                    e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _loading.value = false
        }
    }

    fun refreshingNoteDetail(id: String){
        viewModelScope.launch {
            _refreshing.value = true
            _error.value = null

            repository.getNoteDetail(id)
                .onSuccess {
                        response ->
                    _responseNoteModel.value = response
                }
                .onFailure {
                        e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _refreshing.value = false
        }
    }

    fun updateNoteDetail(id: String, Title: String, Content: String){
        _processing.value = true

        val update = NotesUpdateRequestModel(
            title = Title,
            content = Content
        )

        viewModelScope.launch {
            repository.updateNote(id, update)
                .onSuccess {
                    _processingMessage.value = "la tarea se actualizo correctamente"
                    refreshingNoteDetail(id)
                }
                .onFailure {
                    _processingMessage.value = "hubo un error al intentar actualizar la tarea"
                }

            _processing.value = false
        }
    }

    fun deleteNoteDetail(id: String){
        _processing.value = true

        viewModelScope.launch {
            repository.deleteNote(id)
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


