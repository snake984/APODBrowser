package com.pandora.apodbrowser.picturedetail.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pandora.apodbrowser.LocalNavController
import com.pandora.apodbrowser.LocalPermissionManager
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.FAVORITE_ADDED
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.IS_FAVORITE
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.IS_NOT_FAVORITE
import com.pandora.apodbrowser.ui.Fab
import com.pandora.apodbrowser.ui.Toast
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import kotlinx.coroutines.launch
import com.pandora.apodbrowser.ui.theme.icons.ic_arrow_back
import com.pandora.apodbrowser.ui.theme.icons.ic_close
import com.pandora.apodbrowser.ui.theme.icons.ic_favorite
import com.pandora.apodbrowser.ui.theme.icons.ic_info
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

enum class ContainerState {
    Fab,
    Fullscreen,
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PictureDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PictureDetailViewModel = koinViewModel(),
    pictureItem: PicOfTheDayItem,
) {
    val navController = LocalNavController.current

    val item by rememberSaveable {
        mutableStateOf(pictureItem)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(ic_arrow_back, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
    ) {
        PictureDetailContent(modifier, it, item, viewModel)
    }
}

@Composable
private fun PictureDetailContent(
    modifier: Modifier,
    padding: PaddingValues,
    item: PicOfTheDayItem,
    pictureDetailViewModel: PictureDetailViewModel,
) {
    val favoriteState = pictureDetailViewModel.favoriteState.collectAsStateWithLifecycle()
    pictureDetailViewModel.isPictureFavorite(item)
    val permissionManager = LocalPermissionManager.current
    val coroutineScope = rememberCoroutineScope()
    var explanationVisible by rememberSaveable { mutableStateOf(false) }

    if (explanationVisible) {
        ExplanationView(item = item, onBackPressed = { explanationVisible = false })
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = padding.calculateTopPadding(), bottom = 32.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.12f)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp)),
            imageModel = { item.hdUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            Text(item.title, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = item.copyright?.takeIf { it.isNotBlank() }?.let {
                    "${item.date}${stringResource(R.string.picture_detail_date_separator)}$it"
                } ?: item.date,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            item.explanation?.let {
                Text(it, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = {
                    coroutineScope.launch {
                        if (favoriteState.value == IS_FAVORITE) {
                            pictureDetailViewModel.removeFavorite(item)
                        } else if (favoriteState.value == IS_NOT_FAVORITE &&
                            permissionManager.requestWriteExternalStoragePermission()
                        ) {
                            pictureDetailViewModel.saveFavorite(item)
                        }
                    }
                }) {
                    Icon(ic_favorite, contentDescription = null)
                    androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
                    Text(stringResource(R.string.picture_detail_save))
                }
                TextButton(onClick = { explanationVisible = true }) {
                    Icon(ic_info, contentDescription = null)
                    androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
                    Text(stringResource(R.string.picture_detail_about))
                }
            }
        }
    }
}

@Composable
fun ExplanationView(
    modifier: Modifier = Modifier,
    item: PicOfTheDayItem,
    onBackPressed: () -> Unit,
) {
    item.explanation?.let { explanation ->
        ConstraintLayout(
            modifier = modifier
                .padding(all = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .fillMaxSize()
        ) {
            val (closeButton, publishedDateView, explanationView) = createRefs()

            IconButton(
                modifier = Modifier.constrainAs(closeButton) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                onClick = { onBackPressed() }
            ) {
                Icon(
                    imageVector = ic_close,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Text(
                modifier = Modifier
                    .constrainAs(publishedDateView) {
                        top.linkTo(closeButton.top)
                        bottom.linkTo(closeButton.bottom)
                        start.linkTo(closeButton.end)
                    }
                    .padding(start = 8.dp, end = 16.dp),
                text = LocalDate.parse(item.date)
                    .format(
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                            .withLocale(Locale.CANADA)
                    ),
            )
            Text(
                modifier = Modifier
                    .constrainAs(explanationView) {
                        top.linkTo(publishedDateView.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                text = explanation,
                textAlign = TextAlign.Justify
            )
        }
    } ?: run {
        Toast(messageResId = R.string.no_explanation_found)
    }
}
