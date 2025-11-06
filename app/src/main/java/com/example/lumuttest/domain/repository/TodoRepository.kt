package com.example.lumuttest.domain.repository

import com.example.lumuttest.data.remote.Todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>
    suspend fun getTodoById(id: Int): Todo?
}
