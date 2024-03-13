package com.pandora.apodbrowser.picturedetail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.theme.md_theme_dark_primary
import com.pandora.apodbrowser.ui.theme.thirty_percent_transparent_black
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PictureDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pictureItem: PicOfTheDayItem
) {
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
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(top = it.calculateTopPadding()),
        ) {
            val explanationView = createRef()

            GlideImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { item.hdUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop
                ),
            )
            item.explanation?.let { explanation ->
                Surface(
                    modifier = Modifier
                        .height(140.dp)
                        .constrainAs(explanationView) { bottom.linkTo(parent.bottom) },
                    color = thirty_percent_transparent_black
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                            .verticalScroll(rememberScrollState()),
                        text = explanation,
                        color = md_theme_dark_primary,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}
