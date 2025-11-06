package com.example.lumuttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lumuttest.data.remote.Todo
import com.example.lumuttest.presentation.TodoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TodoViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            AppScreen(viewModel, windowSizeClass.widthSizeClass)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(viewModel: TodoViewModel, widthSizeClass: WindowWidthSizeClass) {
    val isExpanded = widthSizeClass == WindowWidthSizeClass.Expanded
    val selectedTodo by viewModel.selectedTodo.observeAsState()
    val showBackButton = !isExpanded && selectedTodo != null

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            TopAppBar(
                title = { Text(text = if (showBackButton) "Todo Detail" else "Singgih Harseno") },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { viewModel.onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }
    ) { innerPadding ->
        if (isExpanded) {
            TabletLayout(viewModel, modifier = Modifier.padding(innerPadding))
        } else {
            PhoneLayout(viewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun PhoneLayout(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val todos by viewModel.todos.observeAsState(emptyList())
    val selectedTodo by viewModel.selectedTodo.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isDetailLoading by viewModel.isDetailLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    Box(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (error != null) {
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
        } else {
            if (selectedTodo == null) {
                TodoList(todos = todos, onTodoClick = { viewModel.fetchTodoById(it.id) })
            } else {
                if (isDetailLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    TodoDetailScreen(todo = selectedTodo!!, onBack = { viewModel.onBack() })
                }
            }
        }
    }
}

@Composable
fun TabletLayout(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val todos by viewModel.todos.observeAsState(emptyList())
    val selectedTodo by viewModel.selectedTodo.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isDetailLoading by viewModel.isDetailLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    Row(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            }
        } else {
            TodoList(
                todos = todos,
                onTodoClick = { viewModel.fetchTodoById(it.id) },
                modifier = Modifier.weight(0.4f)
            )
            Box(modifier = Modifier.weight(0.6f), contentAlignment = Alignment.Center) {
                if (isDetailLoading) {
                    CircularProgressIndicator()
                } else {
                    selectedTodo?.let {
                        TodoDetail(it, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun TodoList(todos: List<Todo>, onTodoClick: (Todo) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(8.dp)) {
        items(todos) { todo ->
            TodoItem(todo) {
                onTodoClick(todo)
            }
        }
    }
}

@Composable
fun TodoDetailScreen(todo: Todo, onBack: () -> Unit) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(onBackPressedDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        onBackPressedDispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
    TodoDetail(todo = todo, modifier = Modifier.fillMaxSize())
}

@Composable
fun TodoItem(todo: Todo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = todo.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Completed: ${todo.completed}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun TodoDetail(todo: Todo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = todo.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "User ID: ${todo.userId}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "ID: ${todo.id}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Completed: ${todo.completed}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
