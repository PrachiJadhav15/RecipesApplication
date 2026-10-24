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
