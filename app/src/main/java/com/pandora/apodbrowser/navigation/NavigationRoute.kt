package com.pandora.apodbrowser.navigation

sealed interface NavigationRoute {
    val destinationId: String

    fun argsName(): String? = null

    data object Home : NavigationRoute {
        override val destinationId: String = "home"
    }

    data object PictureDetail : NavigationRoute {
        override val destinationId: String = "pictureDetail"

        override fun argsName(): String = "pictureDetailArgs"
    }

    data object Favorites : NavigationRoute {
        override val destinationId: String = "favorites"
    }
}
