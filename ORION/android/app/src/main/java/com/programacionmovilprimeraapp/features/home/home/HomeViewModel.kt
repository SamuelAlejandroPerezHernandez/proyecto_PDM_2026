package com.programacionmovilprimeraapp.features.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import com.programacionmovilprimeraapp.features.task.domain.model.TaskRequestModel
import com.programacionmovilprimeraapp.features.task.domain.model.TaskModel
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.notes.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.features.notes.domain.model.NotesRequestModel
import com.programacionmovilprimeraapp.features.notes.domain.model.NoteModel
import com.programacionmovilprimeraapp.features.notes.domain.repository.NoteRepositoryN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {

    private val repository: TaskRepository =
        TaskRepositoryImp(MyApp.Companion.sessionManager)
    private val repositoryN: NoteRepositoryN =
        NoteRepositoryImp(MyApp.Companion.sessionManager)

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    private val _savingMessage = MutableStateFlow<String?>(null)
    var savingMessage = _savingMessage.asStateFlow()
    private val _upcomingTasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val upcomingTasks = _upcomingTasks.asStateFlow()

    private val _recentNotes = MutableStateFlow<List<NoteModel>>(emptyList())
    val recentNotes = _recentNotes.asStateFlow()

    private val _loadingSummary = MutableStateFlow(false)
    val loadingSummary = _loadingSummary.asStateFlow()

    private val _summaryError = MutableStateFlow<String?>(null)
    val summaryError = _summaryError.asStateFlow()

    init {
        loadSummary()
    }

    fun loadSummary() {
        viewModelScope.launch {
            _loadingSummary.value = true
            _summaryError.value = null

            repository.getUpcomingTasks()
                .onSuccess { tasks -> _upcomingTasks.value = tasks }
                .onFailure { e -> _summaryError.value = e.message }

            repositoryN.getRecentNotes()
                .onSuccess { notes -> _recentNotes.value = notes }
                .onFailure { e -> _summaryError.value = e.message }

            _loadingSummary.value = false
        }
    }

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
                    loadSummary()
                }
                .onFailure {
                        e ->
                    _savingMessage.value = "al ingresar la tarea ocurrio un error: ${e.message}"
                }
            _saving.value = false
        }
    }

    fun InsertNote(CategoryId: String, title:String, content: String){
        _saving.value = true
        val NoteRequest = NotesRequestModel(
            categoryId = CategoryId,
            title = title,
            content = content
        )
        viewModelScope.launch {
            repositoryN.createNote(NoteRequest)
                .onSuccess {
                        response ->
                    _savingMessage.value = "Nota guardad con exito"
                    loadSummary()
                }
                .onFailure {
                        e ->
                    _savingMessage.value = "al ingresar la nota ocurrio un error: ${e.message}"
                }
            _saving.value = false
        }
    }
}