package com.example.qrcraft.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Scanner : NavigationRoute

    @Serializable
    data class Result(val qrCode: String) : NavigationRoute

    @Serializable
    data object CreateQRCode : NavigationRoute

    @Serializable
    data class QrCodeForm(val barcodeType: String) : NavigationRoute

    @Serializable
    data object History : NavigationRoute
}