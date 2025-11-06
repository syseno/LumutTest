package com.example.lumuttest.domain.use_case

import com.example.lumuttest.data.remote.Todo
import com.example.lumuttest.data.repository.TodoRepositoryImpl
import com.example.lumuttest.domain.repository.TodoRepository

class GetTodosUseCase {
    private val repository: TodoRepository = TodoRepositoryImpl()

    suspend fun execute(): List<Todo> {
        return repository.getTodos()
    }
}
