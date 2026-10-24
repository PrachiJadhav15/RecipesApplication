package com.example.recipesapplication.data.remote

import com.example.recipesapplication.data.remote.dto.MealDetailResponse
import com.example.recipesapplication.data.remote.dto.MealListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("filter.php?c=Dessert")
    suspend fun getDessertRecipes(): MealListResponse

    @GET("lookup.php")
    suspend fun getRecipeDetail(@Query("i") id: String): MealDetailResponse

    @GET("search.php")
    suspend fun searchDessertRecipes(@Query("s") query: String): MealListResponse
}
//https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert