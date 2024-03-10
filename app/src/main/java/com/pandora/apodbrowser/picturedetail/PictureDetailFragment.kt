package com.pandora.apodbrowser.picturedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pandora.apodbrowser.databinding.PictureDetailFragmentLayoutBinding
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme
import com.pandora.apodbrowser.ui.theme.thirty_percent_transparent_black

class PictureDetailFragment : Fragment() {

    private lateinit var binding: PictureDetailFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PictureDetailFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDetailContent()
    }

    private fun setupDetailContent() {
        binding.pictureDetailFragmentContentView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                APODBrowserTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        val pictureItem =
                            arguments?.getParcelable(ARGS_KEY) as? PicOfTheDayItem
                        pictureItem?.let {
                            PictureDetailScreen(
                                navController = findNavController(),
                                pictureItem = pictureItem
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ARGS_KEY = "picture_detail_args_key"
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
private fun PictureDetailScreen(
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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
                colors = topAppBarColors(
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
                model = item.hdUrl,
                contentScale = ContentScale.Crop,
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize()
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
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}