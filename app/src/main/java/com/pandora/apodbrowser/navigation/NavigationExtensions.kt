package com.pandora.apodbrowser.navigation

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.get
import androidx.navigation.navArgument

inline fun <reified T : Parcelable> NavController.navigate(
    route: NavigationRoute,
    arguments: T? = null
) {
    val argsBundle = route.argsName()?.let {
        bundleOf(it to arguments)
    } ?: bundleOf()

    return navigate(graph[route.destinationId].id, argsBundle)
}

inline fun <reified T : Parcelable> buildNavArguments(argsName: String): List<NamedNavArgument> =
    listOf(navArgument(argsName) {
        type = NavType.ParcelableType(T::class.java)
    })
