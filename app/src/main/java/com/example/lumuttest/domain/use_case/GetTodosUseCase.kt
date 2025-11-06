package com.example.lumuttest.domain.use_case;

import com.example.lumuttest.data.remote.Todo;
import com.example.lumuttest.data.repository.TodoRepositoryImpl;
import com.example.lumuttest.domain.repository.TodoRepository;

import java.util.List;

public class GetTodosUseCase {
    private final TodoRepository repository;

    public GetTodosUseCase() {
        this.repository = new TodoRepositoryImpl();
    }

    public List<Todo> execute() throws Exception {
        return repository.getTodos();
    }
}