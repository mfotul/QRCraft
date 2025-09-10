package com.example.qrcraft.app.navigation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.qrcraft.scanner.domain.models.BarcodeType
import com.example.qrcraft.scanner.presentation.create_qr.CreateQRCodeScreen
import com.example.qrcraft.scanner.presentation.history.HistoryScreenRoot
import com.example.qrcraft.scanner.presentation.qr_form.QrCodeFormScreenRoot
import com.example.qrcraft.scanner.presentation.result.ResultScreenRoot
import com.example.qrcraft.scanner.presentation.scanner.ScannerScreenRoot
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
                onScanResult = { qrCodeId ->
                    navController.navigate(NavigationRoute.Result(qrCodeId))
                },
                onCloseApp = {
                    ActivityCompat.finishAffinity(activity)
                    exitProcess(0)
                },
                onCreateQRCodeClick = {
                    navController.navigate(NavigationRoute.CreateQRCode) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                },
                onScanHistoryClick = {
                    navController.navigate(NavigationRoute.History) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<NavigationRoute.Result> { backStackEntry ->
            ResultScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<NavigationRoute.CreateQRCode> {
            CreateQRCodeScreen(
                onScanClick = {
                    navController.navigate(NavigationRoute.Scanner) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                },
                onHistoryClick = {
                    navController.navigate(NavigationRoute.History) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
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
                onGenerateQrCodeClick = { qrCodeId ->
                    navController.navigate(NavigationRoute.Result(qrCodeId))
                }
            )
        }
        composable<NavigationRoute.History> {
            HistoryScreenRoot(
                onCreateQRCodeClick = {
                    navController.navigate(NavigationRoute.CreateQRCode) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                },
                onScanClick = {
                    navController.navigate(NavigationRoute.Scanner) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                },
                onItemClick = { qrCodeId ->
                    navController.navigate(NavigationRoute.Result(qrCodeId))
                }
            )
        }
    }
}
