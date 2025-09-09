@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.example.qrcraft.scanner.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrcraft.core.presentation.designsystem.navbars.ScannerBottomNavigation
import com.example.qrcraft.scanner.presentation.history.components.HistoryBottomFade
import com.example.qrcraft.scanner.presentation.history.components.HistoryList
import com.example.qrcraft.scanner.presentation.history.components.HistoryTopBar
import com.example.qrcraft.scanner.presentation.preview.PreviewModel.fakeQrCodes
import com.example.qrcraft.ui.theme.QRCraftTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistoryScreenRoot(
    onCreateQRCodeClick: () -> Unit,
    onScanClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onAction = {
            when (it) {
                HistoryAction.OnCreateQrClick -> onCreateQRCodeClick()
                HistoryAction.OnScanClick -> onScanClick()
                is HistoryAction.OnItemClick -> onItemClick(it.qrCodeId)
                else -> viewModel.onAction(it)
            }
        }
    )
}

@Composable
fun HistoryScreen(
    state : HistoryState,
    onAction: (HistoryAction) -> Unit,
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
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    bottom = 0.dp
                )
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
                            onAction(HistoryAction.OnSwitchHistory(destination))
                        },
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        text = {
                            Text(text = destination.label.asString())
                        },
                    )
                }
            }
            NavHost(
                navController = navController,
                startDestination = startDestination.name,
            ) {
                Destination.entries.forEach { destination ->
                    composable(destination.name) {
                        HistoryList(
                            qrCodeUis = state.qrCodes,
                            onAction = onAction,
                            modifier = Modifier.padding(
                                top = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
                            )
                        )
                    }
                }
            }
            HistoryBottomFade(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            ScannerBottomNavigation(
                onHistoryClick = {
                },
                onScanClick = { onAction(HistoryAction.OnScanClick) },
                onCreateQrClick = { onAction(HistoryAction.OnCreateQrClick) },
                historyChosen = true,
                createQrChosen = false,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp)
            )
        }

    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    QRCraftTheme {
        Surface {
            val fakeState = HistoryState(
                qrCodes = fakeQrCodes
            )

            HistoryScreen(
                state = fakeState,
                onAction = {}
            )
        }
    }
}