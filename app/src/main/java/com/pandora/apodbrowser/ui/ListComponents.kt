package com.pandora.apodbrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow

@Composable
fun FullWidthPictureItem(
    modifier: Modifier = Modifier,
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
fun RandomPicsGrid(
    modifier: Modifier = Modifier,
    dataFlow: Flow<PagingData<PicOfTheDayItem>>,
    onItemClick: (PicOfTheDayItem) -> Unit
) {
    val data = dataFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            items(count = data.itemCount, key = data.itemKey { it.hashCode() }) { index ->
                data[index]?.let { RandomPicsGridCard(item = it, onItemClick = onItemClick) }
            }
        },
    )
}

@Composable
internal fun RandomPicsGridCard(
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
                .height(255.dp),
            imageModel = {
                item.url
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
            loading = {
                LoadingView(
                    modifier = Modifier.size(56.dp),
                    animationResId = R.raw.loading_small
                )
            },
            failure = {
                Text(text = stringResource(id = R.string.pic_loading_failed))
            },
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            }
        )
    }
}

@Composable
fun LatestCollectionRow(
    modifier: Modifier = Modifier,
    data: List<PicOfTheDayItem>,
    onItemClick: (PicOfTheDayItem) -> Unit,
) {
    val rowContentDescription = stringResource(R.string.latest_collection_row_label)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.semantics {
            contentDescription = rowContentDescription
        }
    ) {
        items(items = data, key = { it.hashCode() }) { item ->
            LatestCollectionCard(
                picture = item,
                modifier = Modifier.size(256.dp, 144.dp),
                onItemClick = onItemClick
            )
        }
    }
}