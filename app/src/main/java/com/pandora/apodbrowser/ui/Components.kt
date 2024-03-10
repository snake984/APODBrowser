package com.pandora.apodbrowser.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.fetchpics.model.PicOfTheDay

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
    onValueChange: (String) -> Unit
) {
    val text = rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        value = text.value,
        singleLine = true,
        onValueChange = {
            text.value = it
            onValueChange(it)
        },
        trailingIcon = {
            if (text.value.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable(enabled = true) {
                        text.value = ""
                        onValueChange(text.value)
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        },
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
fun SearchResultsView(
    modifier: Modifier,
    searchResults: List<PicOfTheDayItem>,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),

        ) {
        items(searchResults) {
            FullWidthPictureItem(modifier, it, onItemClick)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FullWidthPictureItem(
    modifier: Modifier,
    item: PicOfTheDayItem,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.clickable {
            onItemClick(item)
        }
    ) {
        GlideImage(
            modifier = modifier.fillMaxWidth(),
            model = item.url,
            contentScale = ContentScale.Crop,
            contentDescription = item.title
        )
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
    modifier: Modifier = Modifier,
    picture: PicOfTheDayItem,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.clickable {
            onItemClick(picture)
        }
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
    data: LazyPagingItems<PicOfTheDayItem>,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            items(count = data.itemCount, key = { data[it]?.hashCode() ?: it }) { index ->
                data[index]?.let { RandomPicsGridCard(item = it, onItemClick = onItemClick) }
            }
            if (data.loadState.append == LoadState.Loading) {
                item {
                    LoadingView()
                }
            }
        },
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun RandomPicsGridCard(
    modifier: Modifier = Modifier,
    item: PicOfTheDayItem,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.clickable { onItemClick(item) }
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
    modifier: Modifier = Modifier,
    data: List<PicOfTheDayItem>,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(items = data, key = { it.url }) { item ->
            LatestCollectionCard(picture = item, modifier =  modifier.size(255.dp, 144.dp), onItemClick =  onItemClick)
        }
    }
}