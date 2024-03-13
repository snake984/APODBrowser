package com.pandora.apodbrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.domain.model.PicOfTheDay
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FullWidthPictureItem(
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
            imageModel = { item.url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
        )
    }
}

@Composable
fun RandomPicsElement(
    modifier: Modifier = Modifier,
    picture: PicOfTheDay,
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            imageModel = { picture.url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
        )
        Text(
            text = picture.date,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

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
            modifier = modifier,
            imageModel = { picture.url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            )
        )
    }
}

@Composable
fun RandomPicsRow(
    modifier: Modifier = Modifier,
    data: List<PicOfTheDay>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(data) { item ->
            RandomPicsElement(picture = item, modifier = modifier)
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
                    LoadingView(
                        modifier = Modifier.size(56.dp),
                        animationResId = R.raw.loading_small
                    )
                }
            }
        },
    )
}

@Composable
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            imageModel = { item.url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
            failure = {
                ErrorView(animationResId = R.raw.space_404)
            }
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
            LatestCollectionCard(
                picture = item,
                modifier = modifier.size(256.dp, 144.dp),
                onItemClick = onItemClick
            )
        }
    }
}