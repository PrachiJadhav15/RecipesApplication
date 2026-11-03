package com.example.recipesapplication.di

import com.example.recipesapplication.data.remote.MealApiService
import com.example.recipesapplication.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMealApiService(): MealApiService {
        return RetrofitClient.apiService
    }
}
