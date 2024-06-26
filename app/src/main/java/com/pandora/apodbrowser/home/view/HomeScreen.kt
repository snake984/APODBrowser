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
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.ui.ErrorView
import com.pandora.apodbrowser.ui.LatestCollectionRow
import com.pandora.apodbrowser.ui.LoadingView
import com.pandora.apodbrowser.ui.RandomPicsGrid
import com.pandora.apodbrowser.ui.SearchBar
import com.pandora.apodbrowser.ui.SearchResultsView
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.domain.errors.NetworkError
import kotlinx.coroutines.launch

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
        HomeContent(
            modifier,
            homeViewModel,
            onItemClick
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(modifier.padding(horizontal = 16.dp)) {
            homeViewModel.updateSearchResults(it)
        }
        Spacer(Modifier.height(16.dp))

        val latestPics by homeViewModel.latestPicsOfTheDay.collectAsStateWithLifecycle()
        val error by homeViewModel.error.collectAsStateWithLifecycle()

        if (latestPics.isNotEmpty() && error == null) {
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
                    LatestCollectionRow(data = latestPics, onItemClick = onItemClick)
                }
                HomeSection(title = R.string.random_pictures) {
                    RandomPicsGrid(dataFlow = homeViewModel.pagedRandomPics, onItemClick = onItemClick)
                }
            }
        } else if (error != null) {
            when (error) {
                is NetworkError -> ErrorView(animationResId = R.raw.lost_connection)
            }
        } else {
            LoadingView(animationResId = R.raw.loading_big)
        }
    }
}

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
