package com.pandora.apodbrowser.home.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.ui.LatestCollectionsRow
import com.pandora.apodbrowser.ui.LoadingView
import com.pandora.apodbrowser.ui.RandomPicsGrid
import com.pandora.apodbrowser.ui.SearchBar
import com.pandora.apodbrowser.ui.SearchResultsView
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import kotlinx.coroutines.launch

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
        )
        content()
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    diComponent: HomeComponent,
    onItemClick: (PicOfTheDayItem) -> Unit = {},
) {
    val homeViewModel: HomeViewModel = viewModel<HomeViewModel> {
        diComponent.homeViewModelFactory().create(HomeViewModel::class.java)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            homeViewModel.fetchLatestPicsOfTheDay()
        }
    }
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val randomPics = homeViewModel.pagedRandomPics.collectAsLazyPagingItems()
        val latestPics by homeViewModel.latestPicsOfTheDay.collectAsStateWithLifecycle()

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))
            SearchBar(Modifier.padding(horizontal = 16.dp)) {
                homeViewModel.updateSearchResults(it)
            }
            Spacer(Modifier.height(16.dp))

            if (latestPics.isNotEmpty() && !randomPics.loadState.hasError) {
                val searchResults by homeViewModel.searchResults.collectAsStateWithLifecycle()
                val searchInput by homeViewModel.searchText.collectAsStateWithLifecycle()

                if (searchInput.isNotEmpty()) {
                    SearchResultsView(
                        searchResults = searchResults,
                        modifier = modifier,
                        onItemClick = onItemClick
                    )
                } else {
                    HomeSection(title = R.string.latest_pics) {
                        LatestCollectionsRow(data = latestPics, onItemClick = onItemClick)
                    }
                    HomeSection(title = R.string.random_pictures) {
                        RandomPicsGrid(data = randomPics, onItemClick = onItemClick)
                    }
                }
            } else {
                LoadingView()
            }
        }
    }
}
