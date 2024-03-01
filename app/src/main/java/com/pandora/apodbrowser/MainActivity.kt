package com.pandora.apodbrowser

import android.os.Bundle
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.pandora.apodbrowser.databinding.MainActivityLayoutBinding
import com.pandora.apodbrowser.ui.theme.APODBrowserTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)
        val navController = findNavController()
        findViewById<ComposeView>(R.id.bottomBarView).apply {
            setContent {
                APODBrowserTheme {
                    APODBrowserBottomNavigation(navController = navController)
                }
            }
        }
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        return navHostFragment.navController
    }
}


@Composable
private fun APODBrowserBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
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
                if (navController.currentDestination?.id != R.id.home) {
                    navController.navigate(R.id.home)
                }
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
fun APODBrowserAppPortrait() {
    APODBrowserTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                APODBrowserBottomNavigation(navController = navController)
            }
        ) { padding ->
            AndroidViewBinding(
                modifier = Modifier.padding(padding),
                factory = MainActivityLayoutBinding::inflate
            )
        }
    }
}