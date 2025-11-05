package com.example.lumuttest.domain.use_case;

import com.example.lumuttest.data.remote.Todo;
import com.example.lumuttest.data.repository.TodoRepositoryImpl;
import com.example.lumuttest.domain.repository.TodoRepository;

public class GetTodoByIdUseCase {
    private final TodoRepository repository;

    public GetTodoByIdUseCase() {
        this.repository = new TodoRepositoryImpl();
    }

    public Todo execute(int id) throws Exception {
        return repository.getTodoById(id);
    }
}