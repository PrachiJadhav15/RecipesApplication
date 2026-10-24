package com.example.recipesapplication.ui.adaptive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipesapplication.ui.screens.RecipeDetailScreen
import com.example.recipesapplication.ui.screens.RecipeListScreen
import com.example.recipesapplication.ui.state.RecipeDetailUiState
import com.example.recipesapplication.ui.viewmodel.RecipeViewModel

@Composable
fun AdaptiveRecipeApp(
    viewModel: RecipeViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val detailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val selectedRecipeId by viewModel.selectedRecipeId.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isExpanded = maxWidth >= 600.dp

        if (isExpanded) {
            // Dual-pane layout
            Row(modifier = Modifier.fillMaxSize()) {
                // Left pane: Recipe Grid/List
                Surface(
                    modifier = Modifier
                        .width(380.dp)
                        .fillMaxHeight(),
                    tonalElevation = 1.dp
                ) {
                    RecipeListScreen(
                        uiState = uiState,
                        selectedRecipeId = selectedRecipeId,
                        onSearchQueryChange = { query -> viewModel.searchRecipes(query) },
                        onRecipeClick = { recipe -> viewModel.selectRecipe(recipe.id) },
                        onRetryClick = { viewModel.retry() },
                        onResetSearchClick = { viewModel.searchRecipes("") }
                    )
                }

                // Right pane: Detail or Placeholder
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (selectedRecipeId == null || detailUiState is RecipeDetailUiState.Idle) {
                        EmptyDetailPlaceholder()
                    } else {
                        RecipeDetailScreen(
                            uiState = detailUiState,
                            onBackClick = null,
                            onRetryClick = { viewModel.retry() }
                        )
                    }
                }
            }
        } else {
            // Single-pane layout
            BackHandler(enabled = selectedRecipeId != null) {
                viewModel.clearSelection()
            }

            if (selectedRecipeId != null) {
                RecipeDetailScreen(
                    uiState = detailUiState,
                    onBackClick = { viewModel.clearSelection() },
                    onRetryClick = { viewModel.retry() }
                )
            } else {
                RecipeListScreen(
                    uiState = uiState,
                    selectedRecipeId = null,
                    onSearchQueryChange = { query -> viewModel.searchRecipes(query) },
                    onRecipeClick = { recipe -> viewModel.selectRecipe(recipe.id) },
                    onRetryClick = { viewModel.retry() },
                    onResetSearchClick = { viewModel.searchRecipes("") }
                )
            }
        }
    }
}

@Composable
private fun EmptyDetailPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🍰",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Select a recipe from the menu to start baking! 🍰",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
