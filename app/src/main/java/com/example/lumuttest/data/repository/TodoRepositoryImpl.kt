package com.example.lumuttest.data.repository

import android.util.Log
import com.example.lumuttest.data.remote.ApiService
import com.example.lumuttest.data.remote.RetrofitClient
import com.example.lumuttest.data.remote.Todo
import com.example.lumuttest.domain.repository.TodoRepository
import java.io.IOException

class TodoRepositoryImpl : TodoRepository {
    private val apiService: ApiService = RetrofitClient.getClient().create(ApiService::class.java)

    override suspend fun getTodos(): List<Todo> {
        return try {
            val todos = apiService.getTodos()
            Log.d("TodoRepository", "getTodos response: $todos")
            todos
        } catch (e: Exception) {
            Log.e("TodoRepository", "Error fetching todos", e)
            throw IOException("Error fetching todos", e)
        }
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return try {
            val todo = apiService.getTodoById(id)
            Log.d("TodoRepository", "getTodoById response: $todo")
            todo
        } catch (e: Exception) {
            Log.e("TodoRepository", "Error fetching todo", e)
            throw IOException("Error fetching todo", e)
        }
    }
}
