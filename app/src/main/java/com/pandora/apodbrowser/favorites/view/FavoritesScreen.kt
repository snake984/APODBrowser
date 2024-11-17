package com.pandora.apodbrowser.favorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.favorites.di.FavoritesComponent
import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModel
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.ui.SimplePicsGrid
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    diComponent: FavoritesComponent,
    onItemClick: (PicOfTheDayItem) -> Unit = {},
) {
    val favoritesViewModel: FavoritesViewModel = viewModel<FavoritesViewModel> {
        diComponent.favoritesViewModelFactory().create(FavoritesViewModel::class.java)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            favoritesViewModel.fetchFavorites()
        }
    }
    FavoritesContent(modifier = modifier.fillMaxSize(), favoritesViewModel) {
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

    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.favorites_screen_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
        )
        SimplePicsGrid(data = favoritePics, onItemClick = onItemClick)
    }
}