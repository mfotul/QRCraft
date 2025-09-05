package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrcraft.scanner.presentation.history.Destination

@Composable
fun HistoryNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.name,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.name) {
                HistoryList(
                    barcodeData = emptyList(),
                    destination = destination
                )
            }
        }
    }
}