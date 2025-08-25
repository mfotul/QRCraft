package com.example.qrcraft.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Scanner : NavigationRoute

    @Serializable
    data class Result(val barcode: String) : NavigationRoute
}