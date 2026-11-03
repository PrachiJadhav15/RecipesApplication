package com.example.recipesapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapplication.data.local.SampleData
import com.example.recipesapplication.data.repository.RecipeRepository
import com.example.recipesapplication.ui.state.RecipeDetailUiState
import com.example.recipesapplication.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    internal val mutableUiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = mutableUiState.asStateFlow()

    internal val mutableDetailUiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Idle)
    val detailUiState: StateFlow<RecipeDetailUiState> = mutableDetailUiState.asStateFlow()

    internal val mutableSelectedRecipeId = MutableStateFlow<String?>(null)
    val selectedRecipeId: StateFlow<String?> = mutableSelectedRecipeId.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            mutableUiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = repository.getDessertRecipes()
            result.fold(
                onSuccess = { recipes ->
                    val isFallback = recipes == SampleData.sampleRecipes
                    mutableUiState.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes,
                            errorMessage = null,
                            isOffline = isFallback
                        )
                    }
                },
                onFailure = {
                    mutableUiState.update {
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
        mutableUiState.update { it.copy(searchQuery = query, isLoading = true, errorMessage = null) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val result = repository.searchRecipes(query)
            result.fold(
                onSuccess = { recipes ->
                    val isFallback = recipes == SampleData.sampleRecipes
                    mutableUiState.update {
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
                    mutableUiState.update {
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

        mutableSelectedRecipeId.value = recipeId

        val existingRecipe = mutableUiState.value.recipes.find { it.id == recipeId }
        if (existingRecipe != null && existingRecipe.instructions.isNotBlank() && existingRecipe.ingredients.isNotEmpty()) {
            mutableDetailUiState.value = RecipeDetailUiState.Success(existingRecipe)
            return
        }

        viewModelScope.launch {
            mutableDetailUiState.value = RecipeDetailUiState.Loading
            val result = repository.getRecipeDetail(recipeId)
            result.fold(
                onSuccess = { recipe ->
                    mutableDetailUiState.value = RecipeDetailUiState.Success(recipe)
                },
                onFailure = { error ->
                    mutableDetailUiState.value = RecipeDetailUiState.Error(
                        error.localizedMessage ?: "Failed to load recipe detail"
                    )
                }
            )
        }
    }

    fun clearSelection() {
        mutableSelectedRecipeId.value = null
        mutableDetailUiState.value = RecipeDetailUiState.Idle
    }

    fun retry() {
        val currentSelectedId = mutableSelectedRecipeId.value
        if (currentSelectedId != null && mutableDetailUiState.value is RecipeDetailUiState.Error) {
            selectRecipe(currentSelectedId)
        } else {
            val currentQuery = mutableUiState.value.searchQuery
            if (currentQuery.isNotBlank()) {
                searchRecipes(currentQuery)
            } else {
                loadRecipes()
            }
        }
    }
}
