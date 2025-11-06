package com.example.lumuttest.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/todos")
    Call<List<Todo>> getTodos();

    @GET("/todos/{id}")
    Call<Todo> getTodoById(@Path("id") int id);
}