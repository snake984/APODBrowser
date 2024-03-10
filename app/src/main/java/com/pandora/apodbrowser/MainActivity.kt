package com.pandora.apodbrowser

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pandora.apodbrowser.di.homeComponent
import com.pandora.apodbrowser.home.di.HomeComponent
import com.pandora.apodbrowser.home.view.HomeScreen
import com.pandora.apodbrowser.navigation.NavigationRoute
import com.pandora.apodbrowser.navigation.buildNavArguments
import com.pandora.apodbrowser.navigation.navigate
import com.pandora.apodbrowser.picturedetail.view.PictureDetailScreen
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APODBrowserTheme {
                APODBrowserAppPortrait(homeComponent = homeComponent())
            }
        }
    }
}


@Composable
private fun APODBrowserBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = {
                if (navController.currentDestination?.route != NavigationRoute.Home.destinationId)
                    navController.navigate(NavigationRoute.Home.destinationId)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_favorites))
            },
            selected = false,
            onClick = {
                //TODO - Navigate to "favorites"
            }
        )
    }
}

@Composable
fun APODBrowserAppPortrait(homeComponent: HomeComponent) {
    APODBrowserTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                APODBrowserBottomNavigation(navController = navController)
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable(route = NavigationRoute.Home.destinationId) {
                    HomeScreen(diComponent = homeComponent) {
                        navController.navigate(NavigationRoute.PictureDetail, it)
                    }
                }
                composable(
                    route = NavigationRoute.PictureDetail.destinationId,
                    arguments = buildNavArguments<PicOfTheDayItem>(NavigationRoute.PictureDetail.argsName())
                ) {
                    val item =
                        it.arguments?.getParcelable<PicOfTheDayItem>(NavigationRoute.PictureDetail.argsName())
                    item?.let {
                        PictureDetailScreen(navController = navController, pictureItem = it)
                    } ?: run {
                        //TODO - SHow error view
                    }
                }
            }
        }
    }
}