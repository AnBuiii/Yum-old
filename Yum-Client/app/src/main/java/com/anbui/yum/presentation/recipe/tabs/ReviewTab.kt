package com.anbui.yum.presentation.recipe.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anbui.yum.domain.model.Review
import com.anbui.yum.presentation.recipe.component.ReviewItem
import com.anbui.yum.presentation.recipe.component.TabTopBar
import com.anbui.yum.ui.theme.YumGreen

@Composable
fun ReviewTab(
    reviews: List<Review>
) {
    LazyColumn {
        item {
            TabTopBar(
                modifier = Modifier.padding(24.dp),
                leading = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.clickable { },
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "", tint = YumGreen)
                        Text("Live a review", fontWeight = FontWeight.SemiBold)
                    }
                },
                trailing = {
                    FilledTonalButton(
                        onClick = {},
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = YumGreen.copy(alpha = 0.1f),
                            contentColor = YumGreen,
                        ),
                    ) {
                        Text("Sort")
                        Icon(Icons.Default.ArrowDropDown, "")
                    }
                },
            )
        }

        items(10){
            ReviewItem()
        }
    }
}