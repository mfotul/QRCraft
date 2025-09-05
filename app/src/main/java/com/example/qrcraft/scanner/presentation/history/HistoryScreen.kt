@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qrcraft.scanner.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.qrcraft.core.presentation.designsystem.navbars.ScannerBottomNavigation
import com.example.qrcraft.scanner.presentation.history.components.HistoryNavHost
import com.example.qrcraft.scanner.presentation.history.components.HistoryTopBar
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun HistoryScreen(
    onCreateQRCodeClick: () -> Unit,
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val startDestination = Destination.SCANNED
    var selectedDestination by remember { mutableIntStateOf(Destination.SCANNED.ordinal) }

    Scaffold(
        topBar = {
            HistoryTopBar()
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            SecondaryTabRow(
                selectedTabIndex = selectedDestination,
                indicator = {
                    TabRowDefaults.SecondaryIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.tabIndicatorOffset(selectedTabIndex = selectedDestination)
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.name)
                            selectedDestination = index
                        },
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        text = {
                            Text(text = destination.label.asString())
                        },
                    )
                }
            }
            HistoryNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(
                    top = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
                )
            )
            ScannerBottomNavigation(
                onHistoryClick = {
                },
                onScanClick = onScanClick,
                onCreateQrClick = onCreateQRCodeClick,
                historyChosen = true,
                createQrChosen = false,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    QRCraftTheme {
        Surface {
            HistoryScreen(
                onCreateQRCodeClick = {},
                onScanClick = {}
            )
        }
    }
}