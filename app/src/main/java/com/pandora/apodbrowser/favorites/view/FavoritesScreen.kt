package com.pandora.apodbrowser.favorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModel
import com.pandora.apodbrowser.ui.FavoritePictureRow
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel(),
    onItemClick: (PicOfTheDayItem) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            viewModel.fetchFavorites()
        }
    }
    FavoritesContent(modifier = modifier.fillMaxSize(), viewModel) {
        onItemClick(it)
    }
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    val favoritePics by favoritesViewModel.favorites.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background).fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
        Text(
            text = stringResource(R.string.favorites_collection_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        }
        item {
            Text(
                text = stringResource(R.string.favorites_collection_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            Text(
                text = stringResource(R.string.favorites_filter_all) +
                    "    " + stringResource(R.string.favorites_filter_nebulae) +
                    "    " + stringResource(R.string.favorites_filter_planets),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
        items(favoritePics) { picture ->
            FavoritePictureRow(picture, onItemClick = onItemClick)
        }
    }
}