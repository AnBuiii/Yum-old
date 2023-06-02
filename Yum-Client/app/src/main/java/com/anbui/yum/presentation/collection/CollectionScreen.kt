package com.anbui.yum.presentation.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.anbui.yum.R
import com.anbui.yum.common.component.YumRecipeCard
import com.anbui.yum.domain.model.Recipe
import org.koin.androidx.compose.getViewModel

val recipes = listOf(
    Recipe(),
    Recipe(),
    Recipe(),
    Recipe(),
    Recipe(),
    Recipe(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    collectionId: String,
    onBack: () -> Unit = {},
    viewModel: CollectionViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.75f),
        ) {
            AsyncImage(
                model = uiState.collection.recipes.firstOrNull()?.imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.5f)),
            ) {}

            Text(
                text = uiState.collection.name,
                style = TextStyle(color = Color.White),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center),

                )

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(24.dp),
            )
        }

        LazyColumn {
            items(uiState.collection.recipes) { recipe ->
                YumRecipeCard(recipe = recipe)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCollection(collectionId)
    }
}
