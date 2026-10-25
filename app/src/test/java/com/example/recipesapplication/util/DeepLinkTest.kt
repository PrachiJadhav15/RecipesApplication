package com.example.recipesapplication.util

import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DeepLinkTest {

    @Test
    fun testQueryParameters() {
        assertEquals("52893", DeepLinkUtils.extractRecipeId("http://com.example.recipes?id=52893"))
        assertEquals("52892", DeepLinkUtils.extractRecipeId("https://com.example.recipes.com?id=52892"))
        assertEquals("52893", DeepLinkUtils.extractRecipeId("https://recipes.example.com?i=52893"))
        assertEquals("52893", DeepLinkUtils.extractRecipeId("https://com.example.recipes?recipeId=52893"))
    }

    @Test
    fun testPathSegments() {
        assertEquals("52893", DeepLinkUtils.extractRecipeId("https://com.example.recipes/recipe/52893"))
        assertEquals("52893", DeepLinkUtils.extractRecipeId("http://com.example.recipes/52893"))
        assertEquals("52893", DeepLinkUtils.extractRecipeId("recipe://recipe/52893"))
    }

    @Test
    fun testCustomSchemes() {
        assertEquals("52893", DeepLinkUtils.extractRecipeId("recipe://52893"))
    }

    @Test
    fun testInvalidEmptyOrMissingURIs() {
        assertNull(DeepLinkUtils.extractRecipeId(null as Uri?))
        assertNull(DeepLinkUtils.extractRecipeId(null as String?))
        assertNull(DeepLinkUtils.extractRecipeId(""))
        assertNull(DeepLinkUtils.extractRecipeId("   "))
        assertNull(DeepLinkUtils.extractRecipeId("http://com.example.recipes/"))
        assertNull(DeepLinkUtils.extractRecipeId("http://com.example.recipes/recipe/"))
        assertNull(DeepLinkUtils.extractRecipeId("http://com.example.recipes?id="))
        assertNull(DeepLinkUtils.extractRecipeId("http://com.example.recipes/home"))
        assertNull(DeepLinkUtils.extractRecipeId("recipe://"))
    }
}
