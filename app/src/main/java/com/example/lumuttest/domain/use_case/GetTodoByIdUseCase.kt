package com.example.lumuttest.domain.use_case

import com.example.lumuttest.data.remote.Todo
import com.example.lumuttest.data.repository.TodoRepositoryImpl
import com.example.lumuttest.domain.repository.TodoRepository

class GetTodoByIdUseCase {
    private val repository: TodoRepository = TodoRepositoryImpl()

    suspend fun execute(id: Int): Todo? {
        return repository.getTodoById(id)
    }
}
