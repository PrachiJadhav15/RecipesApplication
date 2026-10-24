package com.example.recipesapplication.ui.viewmodel

import com.example.recipesapplication.data.local.SampleData
import com.example.recipesapplication.data.model.Ingredient
import com.example.recipesapplication.data.model.Recipe
import com.example.recipesapplication.data.repository.RecipeRepository
import com.example.recipesapplication.ui.state.RecipeDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private class FakeRecipeRepository : RecipeRepository {
        var dessertResult: Result<List<Recipe>> = Result.success(SampleData.sampleRecipes)
        var detailResultMap: MutableMap<String, Result<Recipe>> = mutableMapOf()
        var searchResult: Result<List<Recipe>> = Result.success(SampleData.sampleRecipes)

        override suspend fun getDessertRecipes(): Result<List<Recipe>> = dessertResult

        override suspend fun getRecipeDetail(id: String): Result<Recipe> {
            return detailResultMap[id] ?: Result.success(
                Recipe(
                    id = id,
                    name = "Test Recipe $id",
                    imageUrl = "http://example.com/img.jpg",
                    category = "Dessert",
                    area = "British",
                    instructions = "Mix and bake",
                    ingredients = listOf(Ingredient("Sugar", "1 cup"))
                )
            )
        }

        override suspend fun searchRecipes(query: String): Result<List<Recipe>> = searchResult
    }

    @Test
    fun `loadRecipes sets recipes successfully`() = runTest {
        val fakeRepo = FakeRecipeRepository()
        val customRecipes = listOf(
            Recipe("101", "Apple Pie", "", "Dessert", "American", "Bake it", emptyList())
        )
        fakeRepo.dessertResult = Result.success(customRecipes)

        val viewModel = RecipeViewModel(fakeRepo)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(customRecipes, state.recipes)
        assertNull(state.errorMessage)
        assertFalse(state.isOffline)
    }

    @Test
    fun `searchRecipes updates searchQuery and state`() = runTest {
        val fakeRepo = FakeRecipeRepository()
        val searchList = listOf(
            Recipe("201", "Chocolate Cake", "", "Dessert", "French", "Bake cake", emptyList())
        )
        fakeRepo.searchResult = Result.success(searchList)

        val viewModel = RecipeViewModel(fakeRepo)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchRecipes("Chocolate")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Chocolate", state.searchQuery)
        assertEquals(searchList, state.recipes)
        assertFalse(state.isLoading)
    }

    @Test
    fun `selectRecipe loads detail from repo if not in list with instructions`() = runTest {
        val fakeRepo = FakeRecipeRepository()
        val summaryRecipe = Recipe("52893", "Apple Pie", "", "Dessert", "", "", emptyList())
        fakeRepo.dessertResult = Result.success(listOf(summaryRecipe))

        val fullDetail = Recipe(
            id = "52893",
            name = "Apple Pie",
            imageUrl = "",
            category = "Dessert",
            area = "American",
            instructions = "Bake the pie",
            ingredients = listOf(Ingredient("Apple", "2"))
        )
        fakeRepo.detailResultMap["52893"] = Result.success(fullDetail)

        val viewModel = RecipeViewModel(fakeRepo)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.selectRecipe("52893")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("52893", viewModel.selectedRecipeId.value)
        val detailState = viewModel.detailUiState.value
        assertTrue(detailState is RecipeDetailUiState.Success)
        assertEquals(fullDetail, (detailState as RecipeDetailUiState.Success).recipe)
    }

    @Test
    fun `clearSelection resets selectedId and detailUiState`() = runTest {
        val fakeRepo = FakeRecipeRepository()
        val viewModel = RecipeViewModel(fakeRepo)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.selectRecipe("1")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.clearSelection()

        assertNull(viewModel.selectedRecipeId.value)
        assertEquals(RecipeDetailUiState.Idle, viewModel.detailUiState.value)
    }
}
