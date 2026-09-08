package com.pandora.apodbrowser.home.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.ui.ErrorView
import com.pandora.apodbrowser.ui.TodayPictureCard
import com.pandora.apodbrowser.ui.LoadingView
import com.pandora.apodbrowser.ui.SearchBar
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.domain.errors.NetworkError
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
    onItemClick: (PicOfTheDayItem) -> Unit = {},
) {
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
    val latestPics by homeViewModel.latestPicsOfTheDay.collectAsStateWithLifecycle()
    val error by homeViewModel.error.collectAsStateWithLifecycle()
    val searchResults by homeViewModel.searchResults.collectAsStateWithLifecycle()
    val searchInput by homeViewModel.searchText.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 24.dp, bottom = 28.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp)
    ) {
        item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.home_greeting),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(R.string.home_greeting_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        SearchBar(modifier.padding(horizontal = 16.dp)) {
            homeViewModel.updateSearchResults(it)
        }
        }
        if (searchInput.isNotEmpty()) {
            items(searchResults) { picture ->
                TodayPictureCard(picture, Modifier.padding(horizontal = 16.dp), onItemClick)
            }
        } else if (latestPics.isNotEmpty() && error == null) {
            item {
                Text(stringResource(R.string.home_today_in_space), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 16.dp))
            }
            item {
                TodayPictureCard(latestPics.first(), Modifier.padding(horizontal = 16.dp), onItemClick)
            }
            item {
                Text(stringResource(R.string.home_more_to_explore), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 16.dp))
            }
            items(latestPics.drop(1)) { picture ->
                TodayPictureCard(picture, Modifier.padding(horizontal = 16.dp), onItemClick)
            }
        } else if (error != null) {
            item { ErrorView(animationResId = R.raw.lost_connection) }
        } else {
            item { LoadingView(animationResId = R.raw.loading_big) }
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
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .paddingFromBaseline(top = 28.dp, bottom = 14.dp)
        )
        content()
    }
}
