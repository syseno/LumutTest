package com.example.lumuttest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumuttest.data.remote.Todo
import com.example.lumuttest.domain.use_case.GetTodoByIdUseCase
import com.example.lumuttest.domain.use_case.GetTodosUseCase
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val getTodosUseCase: GetTodosUseCase = GetTodosUseCase()
    private val getTodoByIdUseCase: GetTodoByIdUseCase = GetTodoByIdUseCase()

    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> = _todos

    private val _selectedTodo = MutableLiveData<Todo?>()
    val selectedTodo: LiveData<Todo?> = _selectedTodo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDetailLoading = MutableLiveData<Boolean>()
    val isDetailLoading: LiveData<Boolean> = _isDetailLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = getTodosUseCase.execute()
                _todos.postValue(result)
            } catch (e: Throwable) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchTodoById(id: Int) {
        if (id == -1) {
            _selectedTodo.postValue(null)
            return
        }

        _isDetailLoading.value = true
        viewModelScope.launch {
            try {
                val result = getTodoByIdUseCase.execute(id)
                _selectedTodo.postValue(result)
            } catch (e: Throwable) {
                _error.postValue(e.message)
            } finally {
                _isDetailLoading.postValue(false)
            }
        }
    }

    fun onBack() {
        _selectedTodo.value = null
    }
}
