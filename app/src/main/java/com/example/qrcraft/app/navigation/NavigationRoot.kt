package com.example.qrcraft.app.navigation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.presentation.create_qr.CreateQRCodeScreen
import com.example.qrcraft.scanner.presentation.models.BarcodeType
import com.example.qrcraft.scanner.presentation.qr_form.QrCodeFormScreenRoot
import com.example.qrcraft.scanner.presentation.result.ResultScreen
import com.example.qrcraft.scanner.presentation.scanner.ScannerScreenRoot
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    val activity = (LocalActivity.current as Activity)

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Scanner,
    ) {
        composable<NavigationRoute.Scanner> {
            ScannerScreenRoot(
                onScanResult = { barcodeData ->
                    val jsonBarcode = Json.encodeToString(barcodeData)
                    navController.navigate(NavigationRoute.Result(jsonBarcode))
                },
                onCloseApp = {
                    ActivityCompat.finishAffinity(activity)
                    exitProcess(0)
                },
                onCreateQRCodeClick = {
                    navController.navigate(NavigationRoute.CreateQRCode)
                }
            )
        }
        composable<NavigationRoute.Result> { backStackEntry ->
            val resultRoute: NavigationRoute.Result = backStackEntry.toRoute()
            val barcodeData = Json.decodeFromString<BarcodeData>(resultRoute.barcode)
            ResultScreen(
                barcodeData = barcodeData,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavigationRoute.CreateQRCode> {
            CreateQRCodeScreen(
                onScanClick = {
                    navController.popBackStack()
                },
                onQrTypeClick = { barcodeType ->
                    navController.navigate(NavigationRoute.QrCodeForm(barcodeType.name))
                }
            )
        }
        composable<NavigationRoute.QrCodeForm> { backStackEntry ->
            val qrCodeFormRoute: NavigationRoute.QrCodeForm = backStackEntry.toRoute()
            val barcodeType = BarcodeType.valueOf(qrCodeFormRoute.barcodeType)

            QrCodeFormScreenRoot(
                barcodeType = barcodeType,
                onBackClick = {
                    navController.popBackStack()
                },
                onGenerateQrCodeClick = { barcodeData ->
                    val jsonBarcode = Json.encodeToString(barcodeData)
                    navController.navigate(NavigationRoute.Result(jsonBarcode))
                }
            )
        }
    }
}
