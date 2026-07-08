package com.programacionmovilprimeraapp.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.features.notes.data.repository.NoteRepositoryImp
import com.programacionmovilprimeraapp.features.notes.domain.repository.NoteRepositoryN
import com.programacionmovilprimeraapp.features.task.data.repository.TaskRepositoryImp
import com.programacionmovilprimeraapp.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    // Se reutilizan los mismos repositories de tareas y notas ya existentes,
    // solo para leer las listas y contar cuántas tiene el usuario.
    private val taskRepository: TaskRepository = TaskRepositoryImp(MyApp.Companion.sessionManager)
    private val noteRepository: NoteRepositoryN = NoteRepositoryImp(MyApp.Companion.sessionManager)

    private val _sessionClosed = MutableStateFlow(false)
    val sessionClosed = _sessionClosed.asStateFlow()

    private val _email = MutableStateFlow<String?>(null)
    val email = _email.asStateFlow()

    private val _taskCount = MutableStateFlow(0)
    val taskCount = _taskCount.asStateFlow()

    private val _noteCount = MutableStateFlow(0)
    val noteCount = _noteCount.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadAccountData()
    }

    fun loadAccountData() {
        _email.value = MyApp.Companion.sessionManager.getEmail()
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            taskRepository.getTask()
                .onSuccess { tasks -> _taskCount.value = tasks.size }
                .onFailure { e -> _error.value = e.message }

            noteRepository.getNote()
                .onSuccess { notes -> _noteCount.value = notes.size }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            MyApp.Companion.sessionManager.clearSession()
            _sessionClosed.value = true
        }
    }
}