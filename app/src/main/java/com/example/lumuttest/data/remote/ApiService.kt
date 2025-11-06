package com.example.lumuttest.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    @GET("/todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): Todo
}
