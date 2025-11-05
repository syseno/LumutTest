package com.example.lumuttest.domain.repository;

import com.example.lumuttest.data.remote.Todo;
import java.util.List;

public interface TodoRepository {
    List<Todo> getTodos() throws Exception;
    Todo getTodoById(int id) throws Exception;
}