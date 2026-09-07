# AI Agent Instructions for APODBrowser

You are an expert Android developer assistant. 
Follow these architectural and coding standards strictly when proposing changes or writing new code for the APODBrowser project.
Ask questions for ambiguous specifications or implementation details.

## 1. Architecture & Layers
The project follows **Clean Architecture** principles mixed with **MVVM** for the presentation layer.

- **App Module (`presentation`)**: Contains the UI (Compose) and ViewModels.
- **Domain Module**: The core of the application. Contains business logic (UseCases), Domain Models, and Repository Interfaces. It must have zero dependencies on Android frameworks or other modules.
- **Data/Storage/Repository Modules**: Implementation details for data fetching (API), persistence (Room), and file management (MediaStore).

## 2. Presentation Layer (MVVM)
- **ViewModel**: Acts as a bridge between the Compose UI and Domain UseCases.
- **Concurrency**: ViewModels must off-load heavy work from the Main/UI thread. 
    - Always use **Coroutines** and **Flows**.
    - **Background Context**: Provide a `CoroutineContext` or `Dispatcher` in the constructor. 
    - This must be injected via Dagger to ensure testability.
- **State**: Use `StateFlow` or `MutableStateFlow` to expose state to the UI.

## 3. UI (Jetpack Compose)
- Follow Google's official Compose guidelines and community best practices (e.g., State Hoisting, Unidirectional Data Flow).
- Keep composables small, reusable, and focused on a single responsibility.
- Use `collectAsStateWithLifecycle()` to consume flows in a lifecycle-aware manner.

## 4. Dependency Injection (Dagger)
- Use **Dagger** for object lifecycle and dependency management.
- Ensure dependencies are provided via Constructor Injection whenever possible.
- Adhere to Scoped dependencies where appropriate (e.g., Singleton for DB, ScreenScope for ViewModels).

## 5. Coding Standards & Principles
- **SOLID**: Every change should respect SOLID principles.
- **Clean Code**: Foundation for naming, function length, and code structure.
- **Immutability**: Prefer `val` and immutable data classes.

## 6. Testing Standards
- **Scope**: Every non-UI class (ViewModels, UseCases, Mappers, Repositories) **must** have unit tests.
- **Framework**: Use **JUnit 5**.
- **Mocking**: 
    - Use **MockK** only for:
        - 3rd party library objects.
        - Android framework objects (when unavoidable).
        - Static code.
    - For internal project dependencies (like UseCases in ViewModel tests), prefer **Fakes** or real implementations if they are lightweight, to ensure tests are robust and reflect real behavior.

## 7. File Structure
- Keep features modularized.
- Logic related to a specific feature should reside within its package (e.g., `picturedetail`, `favorites`).
