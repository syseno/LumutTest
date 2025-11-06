package com.example.lumuttest.data.repository;

import com.example.lumuttest.data.remote.ApiService;
import com.example.lumuttest.data.remote.RetrofitClient;
import com.example.lumuttest.data.remote.Todo;
import com.example.lumuttest.domain.repository.TodoRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class TodoRepositoryImpl implements TodoRepository {
    private final ApiService apiService;

    public TodoRepositoryImpl() {
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public List<Todo> getTodos() throws Exception {
        Response<List<Todo>> response = apiService.getTodos().execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Error fetching todos: " + response.code());
        }
    }

    @Override
    public Todo getTodoById(int id) throws Exception {
        Response<Todo> response = apiService.getTodoById(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Error fetching todo: " + response.code());
        }
    }
}