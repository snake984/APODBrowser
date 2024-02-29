package com.pandora.apodbrowser.home.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.pandora.apodbrowser.R
import com.pandora.apodbrowser.databinding.HomeFragmentLayoutBinding
import com.pandora.apodbrowser.di.component
import com.pandora.apodbrowser.home.viewmodel.HomeViewModel
import com.pandora.apodbrowser.home.viewmodel.HomeViewModelFactory
import com.pandora.apodbrowser.ui.LatestCollectionsRow
import com.pandora.apodbrowser.ui.LoadingView
import com.pandora.apodbrowser.ui.RandomPicsGrid
import com.pandora.apodbrowser.ui.SearchBar
import com.pandora.apodbrowser.ui.SearchResultsView
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentLayoutBinding

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel: HomeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupHomeContent()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fetchLatestPicsOfTheDay()
            }
        }
    }

    private fun setupHomeContent() {
        binding.homeFragmentContentView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                APODBrowserTheme {
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
fun HomeSection(
    @StringRes title: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit
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

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val randomPics = viewModel.pagedRandomPics.collectAsLazyPagingItems()
        val latestPics by viewModel.latestPicsOfTheDay.collectAsStateWithLifecycle()

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))
            SearchBar(Modifier.padding(horizontal = 16.dp), viewModel.searchText) {
                viewModel.updateSearchResults(it)
            }
            Spacer(Modifier.height(16.dp))

            if (latestPics.isNotEmpty() && !randomPics.loadState.hasError) {
                val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
                val searchInput by viewModel.searchText.collectAsStateWithLifecycle()

                if (searchInput.isNotBlank()) {
                    SearchResultsView(searchResults, modifier)
                } else {
                    HomeSection(title = R.string.latest_pics) {
                        LatestCollectionsRow(latestPics)
                    }
                    HomeSection(title = R.string.random_pictures) {
                        RandomPicsGrid(data = randomPics)
                    }
                }
            } else {
                LoadingView()
            }
        }

    }
}
