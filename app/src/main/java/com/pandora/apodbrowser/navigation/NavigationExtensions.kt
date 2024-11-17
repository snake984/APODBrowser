package com.pandora.apodbrowser.navigation

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

inline fun <reified T : Parcelable> NavController.navigate(
    route: NavigationRoute,
    arguments: T? = null
) {
    if (arguments != null) {
        route.argsName()?.let { argsName ->
            currentBackStackEntry
                ?.savedStateHandle
                ?.set(argsName, arguments)
        }
    }
    return navigate(route.destinationId)
}

inline fun <reified T : Parcelable> buildNavArguments(argsName: String): List<NamedNavArgument> =
    listOf(navArgument(argsName) {
        type = NavType.ParcelableType(T::class.java)
    })
