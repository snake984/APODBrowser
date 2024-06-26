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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.picturedetail.di.PictureDetailComponent
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.FAVORITE_ADDED
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.IS_FAVORITE
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModel.PictureFavoriteState.IS_NOT_FAVORITE
import com.pandora.apodbrowser.ui.Fab
import com.pandora.apodbrowser.ui.Toast
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

enum class ContainerState {
    Fab,
    Fullscreen,
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PictureDetailScreen(
    modifier: Modifier = Modifier,
    diComponent: PictureDetailComponent,
    navController: NavController,
    pictureItem: PicOfTheDayItem
) {
    val item by rememberSaveable {
        mutableStateOf(pictureItem)
    }
    val viewModel: PictureDetailViewModel = viewModel<PictureDetailViewModel> {
        diComponent.pictureDetailViewModelFactory().create(PictureDetailViewModel::class.java)
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
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = padding.calculateTopPadding()),
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = { item.hdUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
        )

        Column(
            modifier = modifier.align(Alignment.BottomEnd),
        ) {
            val favoriteState = pictureDetailViewModel.favoriteState.collectAsStateWithLifecycle()
            pictureDetailViewModel.isPictureFavorite(item)
            var containerState by rememberSaveable { mutableStateOf(ContainerState.Fab) }
            val density = LocalDensity.current

            AnimatedVisibility(visible = containerState == ContainerState.Fab,
                enter = expandVertically() + slideInHorizontally {
                    with(density) { 600.dp.roundToPx() }
                },
                exit = slideOutHorizontally {
                    with(density) { 600.dp.roundToPx() }
                } + shrinkVertically() + fadeOut()
            ) {
                val iconColor =
                    if (favoriteState.value == IS_FAVORITE || favoriteState.value == FAVORITE_ADDED) {
                        Color.Red
                    } else {
                        LocalContentColor.current
                    }

                Fab(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 16.dp),
                    icon = Icons.Filled.Favorite,
                    tintColor = iconColor,
                    contentDescription = R.string.add_to_favorites,
                    onClick = {
                        if (favoriteState.value == IS_FAVORITE) {
                            pictureDetailViewModel.removeFavorite(item)
                        } else if (favoriteState.value == IS_NOT_FAVORITE) {
                            pictureDetailViewModel.saveFavorite(item)
                        }
                    }
                )
            }

            AnimatedContent(
                targetState = containerState,
                label = "container transform",
            ) { state ->
                when (state) {
                    ContainerState.Fab -> Fab(
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 16.dp),
                        icon = Icons.Filled.Info,
                        contentDescription = R.string.show_explanation,
                        onClick = { containerState = ContainerState.Fullscreen }
                    )

                    ContainerState.Fullscreen -> ExplanationView(item = item, onBackPressed = {
                        containerState = ContainerState.Fab
                    })
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
                    imageVector = Icons.Filled.Close,
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
