package com.pandora.apodbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pandora.apodbrowser.favorites.view.FavoritesScreen
import com.pandora.apodbrowser.home.view.HomeScreen
import com.pandora.apodbrowser.navigation.NavigationRoute
import com.pandora.apodbrowser.navigation.navigate
import com.pandora.apodbrowser.permissions.PermissionManager
import com.pandora.apodbrowser.permissions.PermissionManagerImpl
import com.pandora.apodbrowser.picturedetail.view.PictureDetailScreen
import com.pandora.apodbrowser.ui.model.PicOfTheDayItem
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme
import com.pandora.apodbrowser.ui.theme.icons.ic_favorite
import com.pandora.apodbrowser.ui.theme.icons.ic_home

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionManager: PermissionManager =
            PermissionManagerImpl(this)

        setContent {
            APODBrowserTheme {
                CompositionLocalProvider(LocalPermissionManager provides permissionManager) {
                    APODBrowserAppPortrait(
                    )
                }
            }
        }
    }
}


@Composable
private fun APODBrowserBottomNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ic_home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = currentRoute == NavigationRoute.Home.destinationId,
            onClick = {
                if (navController.currentDestination?.route != NavigationRoute.Home.destinationId)
                    navController.navigate(NavigationRoute.Home.destinationId)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ic_favorite,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_favorites))
            },
            selected = currentRoute == NavigationRoute.Favorites.destinationId,
            onClick = {
                if (navController.currentDestination?.route != NavigationRoute.Favorites.destinationId)
                    navController.navigate(NavigationRoute.Favorites.destinationId)
            }
        )
    }
}

@Composable
fun APODBrowserAppPortrait(
) {
    APODBrowserTheme {
        val navController = rememberNavController()

        CompositionLocalProvider(LocalNavController provides navController) {
            Scaffold(
                bottomBar = {
                    APODBrowserBottomNavigation()
                }
            ) { padding ->

                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable(route = NavigationRoute.Home.destinationId) {
                        HomeScreen {
                            navController.navigate(NavigationRoute.PictureDetail, it)
                        }
                    }
                    composable(
                        route = NavigationRoute.PictureDetail.destinationId
                    ) {
                        val item =
                            navController.previousBackStackEntry?.savedStateHandle?.get<PicOfTheDayItem>(
                                NavigationRoute.PictureDetail.argsName()
                            )
                        item?.let {
                            PictureDetailScreen(
                                pictureItem = it
                            )
                        } ?: run {
                            //TODO - SHow error view
                        }
                    }
                    composable(route = NavigationRoute.Favorites.destinationId) {
                        FavoritesScreen {
                            navController.navigate(NavigationRoute.PictureDetail, it)
                        }
                    }
                }
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }
val LocalPermissionManager = compositionLocalOf<PermissionManager> { error("No activity found!") }