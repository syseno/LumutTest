package com.example.lumuttest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.lumuttest.data.remote.Todo;
import com.example.lumuttest.domain.use_case.GetTodoByIdUseCase;
import com.example.lumuttest.domain.use_case.GetTodosUseCase;

import java.util.List;

public class TodoViewModel extends ViewModel {
    private final GetTodosUseCase getTodosUseCase;
    private final GetTodoByIdUseCase getTodoByIdUseCase;
    private final MutableLiveData<List<Todo>> _todos = new MutableLiveData<>();
    public final LiveData<List<Todo>> todos = _todos;
    private final MutableLiveData<Todo> _selectedTodo = new MutableLiveData<>();
    public final LiveData<Todo> selectedTodo = _selectedTodo;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<Boolean> _isDetailLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isDetailLoading = _isDetailLoading;
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    public TodoViewModel() {
        this.getTodosUseCase = new GetTodosUseCase();
        this.getTodoByIdUseCase = new GetTodoByIdUseCase();
        fetchTodos();
    }

    private void fetchTodos() {
        _isLoading.setValue(true);
        new Thread(() -> {
            try {
                List<Todo> result = getTodosUseCase.execute();
                _todos.postValue(result);
            } catch (Exception e) {
                _error.postValue(e.getMessage());
            } finally {
                _isLoading.postValue(false);
            }
        }).start();
    }

    public void fetchTodoById(int id) {
        if (id == -1) {
            _selectedTodo.postValue(null);
            return;
        }

        _isDetailLoading.setValue(true);
        new Thread(() -> {
            try {
                Todo result = getTodoByIdUseCase.execute(id);
                _selectedTodo.postValue(result);
            } catch (Exception e) {
                _error.postValue(e.getMessage());
            } finally {
                _isDetailLoading.postValue(false);
            }
        }).start();
    }

    public void onBack() {
        _selectedTodo.setValue(null);
    }
}