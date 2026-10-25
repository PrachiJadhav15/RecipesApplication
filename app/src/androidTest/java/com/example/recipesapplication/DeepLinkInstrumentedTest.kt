package com.example.recipesapplication

import android.content.Intent
import android.net.Uri
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipesapplication.ui.viewmodel.RecipeViewModel
import com.example.recipesapplication.util.DeepLinkUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeepLinkInstrumentedTest {

    @Test
    fun testDeepLinkUtilsWithAndroidUri() {
        // Query Parameters
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes?id=52893")))
        assertEquals("52892", DeepLinkUtils.extractRecipeId(Uri.parse("https://com.example.recipes.com?id=52892")))
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("https://recipes.example.com?i=52893")))
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("https://com.example.recipes?recipeId=52893")))

        // Path Segments
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("https://com.example.recipes/recipe/52893")))
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes/52893")))
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("recipe://recipe/52893")))

        // Custom Schemes
        assertEquals("52893", DeepLinkUtils.extractRecipeId(Uri.parse("recipe://52893")))

        // Invalid / Empty URIs
        assertNull(DeepLinkUtils.extractRecipeId(null as Uri?))
        assertNull(DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes/")))
        assertNull(DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes/recipe/")))
        assertNull(DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes?id=")))
        assertNull(DeepLinkUtils.extractRecipeId(Uri.parse("http://com.example.recipes/home")))
    }

    @Test
    fun testMainActivityLaunchWithDeepLinkIntent() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://com.example.recipes/recipe/52893")).apply {
            setClassName(
                ApplicationProvider.getApplicationContext(),
                "com.example.recipesapplication.MainActivity"
            )
        }

        val scenario = ActivityScenario.launch<MainActivity>(intent)
        scenario.onActivity { activity ->
            val extractedId = DeepLinkUtils.extractRecipeId(activity.intent?.data)
            assertEquals("52893", extractedId)
        }
        scenario.close()
    }

    @Test
    fun testMainActivityHandleDeepLink() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            val viewModel = RecipeViewModel()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://com.example.recipes?id=52893"))

            activity.handleDeepLink(intent, viewModel)

            assertEquals("52893", viewModel.selectedRecipeId.value)
        }
        scenario.close()
    }
}
