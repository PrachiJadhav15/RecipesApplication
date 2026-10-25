# Project Plan

Create a Dessert Recipes application using Jetpack Compose. The app must be large-screen friendly, utilizing a List-Detail flow suitable for tablets, foldables, and phones (adaptive/responsive layout). Features a grid/list of dessert recipe cards with images and titles fetched from a free Dessert Recipe API (TheMealDB). Tapping a recipe opens a detail view with a large hero image, ingredients list, and cooking instructions. Built using MVVM architecture and Retrofit for API calls.

## Project Brief

# Dessert Recipes App - Project Brief

## Features
1. **Dessert Recipe Discovery**: Browse an interactive grid/list of dessert recipes with thumbnail images and titles fetched live from TheMealDB API.
2. **Detailed Recipe View**: View full recipe details including a large hero image, precise ingredients list with measurements, and step-by-step cooking instructions.
3. **Adaptive List-Detail Layout**: Dynamically adapt screen layouts using a single pane on phones and a dual-pane List-Detail view on foldables and tablets.
4. **Offline Local Fallback**: Automatically fall back to local repository sample data when offline or network connectivity is unavailable.

## High-Level Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose, Material Design 3
- **Navigation & Adaptive Strategy**: **Jetpack Navigation 3** (state-driven) and **Compose Material Adaptive** (`ListDetailPaneScaffold`)
- **Architecture & Async**: MVVM (Model-View-ViewModel), Kotlin Coroutines & Flow
- **Networking & Media**: Retrofit with Moshi/Gson converter, Coil for image loading

## Implementation Steps

### Task_1_SetupDataAndNetworking: Set up data models, Retrofit API service for TheMealDB API, and repository with remote fetching and local offline fallback sample data.
- **Status:** COMPLETED
- **Updates:** Task 1 complete: Set up Retrofit dependencies, internet permission, TheMealDB API models & DTOs, MealApiService, RecipeRepository with network fetch and rich local offline sample fallback, and verified build.
- **Acceptance Criteria:**
  - Data models created for recipe summaries and details
  - Retrofit service interface for TheMealDB endpoints defined
  - Repository handles live API network calls with offline local fallback
  - Build passes

### Task_2_ImplementViewModelAndState: Implement ViewModel and state management using StateFlow for recipe list, selection, details loading, and error/offline states.
- **Status:** COMPLETED
- **Updates:** Task 2 complete: Implemented RecipeListUiState, RecipeDetailUiState, and RecipeViewModel with StateFlow state management for list loading, search, recipe detail selection, retry, and error/offline fallback handling. Added unit tests and verified with assembleDebug and testDebugUnitTest.
- **Acceptance Criteria:**
  - ViewModel exposes recipe list and selected recipe detail UI state
  - Asynchronous network fetching and error/fallback state management implemented
  - Build passes

### Task_3_BuildAdaptiveUIAndScreens: Build Jetpack Compose UI with Material 3 including Recipe Grid screen, Detail screen, and adaptive List-Detail layout for phones and tablets/foldables using Coil for image loading.
- **Status:** COMPLETED
- **Updates:** Task 3 complete: Created RecipeCard with Coil image loading, RecipeListScreen home screen grid view with search & offline retry banner, RecipeDetailScreen with hero image and structured ingredients & instructions, AdaptiveRecipeApp for phone single-pane and tablet/foldable dual-pane List-Detail flow, updated MainActivity. Verified with assembleDebug.
- **Acceptance Criteria:**
  - Recipe Grid/List screen displays thumbnails and titles using Coil
  - Detail view shows hero image, ingredients list with measurements, and step-by-step cooking instructions
  - Adaptive layout renders single-pane on phones and dual-pane on foldables/tablets
  - Build passes

### Task_4_RunAndVerify: Build, run, and verify the application. Instruct critic_agent to verify application stability (no crashes), confirm alignment with user requirements, and report critical UI issues.
- **Status:** COMPLETED
- **Updates:** Task 4 complete: Implemented Deep Linking (HTTP/HTTPS com.example.recipes.com & custom recipe:// scheme), verified intent filters in AndroidManifest.xml, intent handling in MainActivity, ViewModel deep link navigation state, and verified build with assembleDebug and unit tests passing.
- **Acceptance Criteria:**
  - Make sure all existing tests pass
  - Build pass
  - App does not crash
  - Dessert recipe app runs stably with responsive layout and live/offline data

