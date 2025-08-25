package com.example.qrcraft.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.presentation.result.ResultScreen
import com.example.qrcraft.scanner.presentation.scanner.ScannerScreenRoot
import com.example.qrcraft.scanner.presentation.scanner.components.SetStatusBarIconsColor
import kotlinx.serialization.json.Json

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    SetStatusBarIconsColor(darkIcons = false)

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Scanner,
    ) {
        composable<NavigationRoute.Scanner> {
            ScannerScreenRoot(
                onScanResult = { barcodeData ->
                    val jsonBarcode = Json.encodeToString(barcodeData)
                    navController.navigate(NavigationRoute.Result(jsonBarcode))
                }
            )
        }
        composable<NavigationRoute.Result> { backStackEntry ->
            val result: NavigationRoute.Result = backStackEntry.toRoute()
            val barcodeData = Json.decodeFromString<BarcodeData>(result.barcode)
            ResultScreen(
                barcodeData = barcodeData,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}