package com.pandora.apodbrowser.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.pandora.apodbrowser.R
import com.pandora.fetchpics.model.PicOfTheDay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoadingView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
        Text(text = "Loading")
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchInputFlow: StateFlow<String>,
    onValueChange: (String) -> Unit
) {
    val text by searchInputFlow.collectAsStateWithLifecycle()

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        }, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ), placeholder = {
            Text(stringResource(R.string.placeholder_search))
        }, modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun SearchResultsView(
    searchResults: List<PicOfTheDay>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),

        ) {
        items(searchResults) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = modifier
            ) {
                GlideImage(
                    modifier = modifier.fillMaxWidth(),
                    model = it.url,
                    contentScale = ContentScale.Crop,
                    contentDescription = it.title
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RandomPicsElement(
    picture: PicOfTheDay, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            model = picture.url,
            contentScale = ContentScale.Crop,
            contentDescription = picture.title,
            failure = placeholder(android.R.drawable.ic_menu_camera)
        )
        Text(
            text = picture.date,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LatestCollectionCard(
    picture: PicOfTheDay, modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        GlideImage(
            model = picture.url,
            modifier = modifier,
            contentScale = ContentScale.Crop,
            contentDescription = picture.title,
            transition = CrossFade
        )
    }
}

@Composable
fun RandomPicsRow(
    data: List<PicOfTheDay>, modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(data) { item ->
            RandomPicsElement(item, modifier)
        }
    }
}

@Composable
fun RandomPicsGrid(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<PicOfTheDay>
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            items(count = data.itemCount) { index ->
                data[index]?.let { RandomPicsGridCard(it) }
            }
        },
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun RandomPicsGridCard(item: PicOfTheDay) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        GlideImage(
            model = item.url,
            contentScale = ContentScale.Crop,
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun LatestCollectionsRow(
    data: List<PicOfTheDay>, modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(items = data, key = { it.url }) { item ->
            LatestCollectionCard(item, modifier.size(255.dp, 144.dp))
        }
    }
}