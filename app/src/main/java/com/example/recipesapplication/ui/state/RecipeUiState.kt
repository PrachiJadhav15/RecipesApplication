package com.example.recipesapplication.ui.state

import com.example.recipesapplication.data.model.Recipe

data class RecipeListUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null,
    val isOffline: Boolean = false
) {
    val Loading: Boolean get() = isLoading
}

typealias RecipeUiState = RecipeListUiState

sealed interface RecipeDetailUiState {
    data object Idle : RecipeDetailUiState
    data object Loading : RecipeDetailUiState
    data class Success(val recipe: Recipe) : RecipeDetailUiState
    data class Error(val message: String) : RecipeDetailUiState
}
