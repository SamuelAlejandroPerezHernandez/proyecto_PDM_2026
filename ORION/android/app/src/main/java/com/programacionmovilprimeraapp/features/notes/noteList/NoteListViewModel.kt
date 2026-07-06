package com.programacionmovilprimeraapp.features.notes.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.notes.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.notes.domain.repository.NoteRepositoryN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListViewModel(): ViewModel(){
    private val repository: NoteRepositoryN = NoteRepositoryImp(MyApp.Companion.sessionManager)

    private val _NoteList = MutableStateFlow<List<NoteModel>>(emptyList())
    val noteList = _NoteList.asStateFlow()

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

    fun loadNote(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getNote()
                .onSuccess {
                    note ->
                    _NoteList.value = note
                }
                .onFailure {
                    e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _loading.value = false
        }
    }

    fun refreshingNote(){
        viewModelScope.launch {
            _refreshing.value = true
            _error.value = null

            repository.getNote()
                .onSuccess {
                        note ->
                    _NoteList.value = note
                }
                .onFailure {
                        e ->
                    _error.value = e.message
                    println(" ERROR EN REPOSITORIO ANDROID: ${e.localizedMessage}")
                }

            _refreshing.value = false
        }
    }
}