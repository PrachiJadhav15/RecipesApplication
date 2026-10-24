package com.example.recipesapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapplication.data.local.SampleData
import com.example.recipesapplication.data.repository.RecipeRepository
import com.example.recipesapplication.data.repository.RecipeRepositoryImpl
import com.example.recipesapplication.ui.state.RecipeDetailUiState
import com.example.recipesapplication.ui.state.RecipeListUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository = RecipeRepositoryImpl()
) : ViewModel() {

    internal val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    internal val _detailUiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Idle)
    val detailUiState: StateFlow<RecipeDetailUiState> = _detailUiState.asStateFlow()

    internal val _selectedRecipeId = MutableStateFlow<String?>(null)
    val selectedRecipeId: StateFlow<String?> = _selectedRecipeId.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = repository.getDessertRecipes()
            result.fold(
                onSuccess = { recipes ->
                    val isFallback = recipes == SampleData.sampleRecipes
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes,
                            errorMessage = null,
                            isOffline = isFallback
                        )
                    }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipes = SampleData.sampleRecipes,
                            errorMessage = null,
                            isOffline = true
                        )
                    }
                }
            )
        }
    }

    fun searchRecipes(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true, errorMessage = null) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val result = repository.searchRecipes(query)
            result.fold(
                onSuccess = { recipes ->
                    val isFallback = recipes == SampleData.sampleRecipes
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes,
                            errorMessage = if (recipes.isEmpty()) "No recipes found" else null,
                            isOffline = isFallback
                        )
                    }
                },
                onFailure = { error ->
                    val filteredSample = SampleData.sampleRecipes.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipes = filteredSample,
                            errorMessage = if (filteredSample.isEmpty()) "No recipes found" else null,
                            isOffline = true
                        )
                    }
                }
            )
        }
    }

    fun selectRecipe(recipeId: String?) {
        if (recipeId == null) {
            clearSelection()
            return
        }

        _selectedRecipeId.value = recipeId

        val existingRecipe = _uiState.value.recipes.find { it.id == recipeId }
        if (existingRecipe != null && existingRecipe.instructions.isNotBlank() && existingRecipe.ingredients.isNotEmpty()) {
            _detailUiState.value = RecipeDetailUiState.Success(existingRecipe)
            return
        }

        viewModelScope.launch {
            _detailUiState.value = RecipeDetailUiState.Loading
            val result = repository.getRecipeDetail(recipeId)
            result.fold(
                onSuccess = { recipe ->
                    _detailUiState.value = RecipeDetailUiState.Success(recipe)
                },
                onFailure = { error ->
                    _detailUiState.value = RecipeDetailUiState.Error(
                        error.localizedMessage ?: "Failed to load recipe detail"
                    )
                }
            )
        }
    }

    fun clearSelection() {
        _selectedRecipeId.value = null
        _detailUiState.value = RecipeDetailUiState.Idle
    }

    fun retry() {
        val currentSelectedId = _selectedRecipeId.value
        if (currentSelectedId != null && _detailUiState.value is RecipeDetailUiState.Error) {
            selectRecipe(currentSelectedId)
        } else {
            val currentQuery = _uiState.value.searchQuery
            if (currentQuery.isNotBlank()) {
                searchRecipes(currentQuery)
            } else {
                loadRecipes()
            }
        }
    }
}
