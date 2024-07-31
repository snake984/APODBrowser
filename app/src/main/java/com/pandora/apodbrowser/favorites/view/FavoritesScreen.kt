package com.pandora.apodbrowser.favorites.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pandora.apodbrowser.favorites.di.FavoritesComponent
import com.pandora.apodbrowser.favorites.viewmodel.FavoritesViewModel
import com.pandora.apodbrowser.ui.SimplePicsGrid
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    diComponent: FavoritesComponent,
) {

}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        SimplePicsGrid(data = favoritesViewModel) {

        }
    }
}