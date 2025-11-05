# Singgih Harseno - Lumut Test Project

This is a sample Android application that demonstrates a modern, adaptive UI built with Jetpack Compose. The app fetches a list of to-do items from a public API and displays them in a master-detail layout that adjusts to different screen sizes.

## Features

*   **Fetches Data from API:** Retrieves a list of to-dos from `https://jsonplaceholder.typicode.com/todos`.
*   **Adaptive UI:** The layout automatically adapts to the screen size:
    *   **Phones:** Displays a list of to-dos. Tapping an item opens a separate detail screen.
    *   **Tablets:** Displays a side-by-side (master-detail) view with the list on the left and the detail pane on the right.
*   **Paper-like Theme:** A custom UI theme that gives the app a clean, flat, and paper-like appearance.
*   **Clean Architecture:** Built using the MVVM (Model-View-ViewModel) pattern with a repository and use cases to separate concerns and improve maintainability.

## Architecture

The project follows the principles of Clean Architecture, organized into three main layers:

1.  **Data Layer:** Responsible for providing data to the app. It includes:
    *   **Remote:** Retrofit setup (`ApiService`, `RetrofitClient`) for network requests.
    *   **Repository:** An implementation of the Repository pattern (`TodoRepositoryImpl`) to abstract the data source.

2.  **Domain Layer:** Contains the core business logic of the app.
    *   **Repository Interface:** A `TodoRepository` interface that defines the contract for the data layer.
    *   **Use Cases:** `GetTodosUseCase` and `GetTodoByIdUseCase` encapsulate specific business rules.

3.  **Presentation Layer:** Responsible for the UI and handling user interactions.
    *   **ViewModel:** `TodoViewModel` connects the UI to the business logic, exposing data via `LiveData`.
    *   **UI (Compose):** The entire UI is built with Jetpack Compose (`MainActivity`), including adaptive layouts using `WindowSizeClass`.

## Libraries Used

*   **Jetpack Compose:** For building the user interface.
    *   `androidx.compose.material3` for Material Design components.
    *   `androidx.compose.material3:material3-window-size-class` for creating adaptive layouts.
*   **Retrofit:** For making network requests to the REST API.
*   **ViewModel & LiveData:** For managing UI-related data in a lifecycle-aware way.

## How to Run

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Build and run the app on an Android device or emulator.
