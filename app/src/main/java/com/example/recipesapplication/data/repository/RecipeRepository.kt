package com.example.recipesapplication.data.repository

import com.example.recipesapplication.data.local.SampleData
import com.example.recipesapplication.data.model.Recipe
import com.example.recipesapplication.data.remote.MealApiService
import com.example.recipesapplication.data.remote.RetrofitClient
import com.example.recipesapplication.data.remote.dto.toDomain

interface RecipeRepository {
    suspend fun getDessertRecipes(): Result<List<Recipe>>
    suspend fun getRecipeDetail(id: String): Result<Recipe>
    suspend fun searchRecipes(query: String): Result<List<Recipe>>
}

class RecipeRepositoryImpl(
    private val apiService: MealApiService = RetrofitClient.instance
) : RecipeRepository {

    override suspend fun getDessertRecipes(): Result<List<Recipe>> {
        return try {
            val response = apiService.getDessertRecipes()
            val remoteRecipes = response.meals?.map { it.toDomain() }
            if (!remoteRecipes.isNullOrEmpty()) {
                Result.success(remoteRecipes)
            } else {
                Result.success(SampleData.sampleRecipes)
            }
        } catch (e: Exception) {
            Result.success(SampleData.sampleRecipes)
        }
    }

    override suspend fun getRecipeDetail(id: String): Result<Recipe> {
        return try {
            val response = apiService.getRecipeDetail(id)
            val mealDto = response.meals?.firstOrNull()
            if (mealDto != null) {
                Result.success(mealDto.toDomain())
            } else {
                val sampleMatch = SampleData.sampleRecipes.firstOrNull { it.id == id }
                if (sampleMatch != null) {
                    Result.success(sampleMatch)
                } else {
                    Result.failure(Exception("Recipe not found with id: $id"))
                }
            }
        } catch (e: Exception) {
            val sampleMatch = SampleData.sampleRecipes.firstOrNull { it.id == id }
            if (sampleMatch != null) {
                Result.success(sampleMatch)
            } else {
                Result.success(SampleData.sampleRecipes.first())
            }
        }
    }

    override suspend fun searchRecipes(query: String): Result<List<Recipe>> {
        if (query.isBlank()) {
            return getDessertRecipes()
        }
        return try {
            val response = apiService.searchDessertRecipes(query)
            val remoteSummaries = response.meals?.map { it.toDomain() }
            if (!remoteSummaries.isNullOrEmpty()) {
                Result.success(remoteSummaries)
            } else {
                val filteredSample = SampleData.sampleRecipes.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true) ||
                    it.area.contains(query, ignoreCase = true)
                }
                Result.success(filteredSample)
            }
        } catch (e: Exception) {
            val filteredSample = SampleData.sampleRecipes.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true) ||
                it.area.contains(query, ignoreCase = true)
            }
            Result.success(filteredSample)
        }
    }
}
