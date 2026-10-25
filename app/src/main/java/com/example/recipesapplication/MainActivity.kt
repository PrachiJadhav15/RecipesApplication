package com.example.recipesapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipesapplication.ui.adaptive.AdaptiveRecipeApp
import com.example.recipesapplication.ui.theme.RecipesApplicationTheme
import com.example.recipesapplication.ui.viewmodel.RecipeViewModel
import com.example.recipesapplication.util.DeepLinkUtils

@Composable
fun RecipesTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    RecipesApplicationTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content,
    )
}

class MainActivity : ComponentActivity() {
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleDeepLink(intent, viewModel)
        setContent {
            RecipesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AdaptiveRecipeApp(viewModel = viewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent, viewModel)
    }

    fun handleDeepLink(intent: Intent?, viewModel: RecipeViewModel) {
        val recipeId = DeepLinkUtils.extractRecipeId(intent?.data)
        if (!recipeId.isNullOrBlank()) {
            viewModel.selectRecipe(recipeId)
        }
    }
}
