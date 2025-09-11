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
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrcraft.R
import com.example.qrcraft.core.presentation.designsystem.navbars.ScannerBottomNavigation
import com.example.qrcraft.scanner.presentation.history.components.HistoryBottomFade
import com.example.qrcraft.scanner.presentation.history.components.HistoryBottomSheet
import com.example.qrcraft.scanner.presentation.history.components.HistoryList
import com.example.qrcraft.scanner.presentation.history.components.HistoryTopBar
import com.example.qrcraft.scanner.presentation.history.models.Destination
import com.example.qrcraft.scanner.presentation.models.QrCodeUi
import com.example.qrcraft.scanner.presentation.preview.PreviewModel.fakeQrCodes
import com.example.qrcraft.ui.theme.QRCraftTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistoryScreenRoot(
    onCreateQRCodeClick: () -> Unit,
    onScanClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        qrCodeUis = state.qrCodes,
        destination = state.destination,
        showBottomSheet = state.showBottomSheet,
        onAction = {
            when (it) {
                HistoryAction.OnCreateQrClick -> onCreateQRCodeClick()
                HistoryAction.OnScanClick -> onScanClick()
                is HistoryAction.OnItemClick -> onItemClick(it.qrCodeUi.id)
                else -> viewModel.onAction(it)
            }
        }
    )
}

@Composable
fun HistoryScreen(
    qrCodeUis: List<QrCodeUi>,
    destination: Destination,
    showBottomSheet: Boolean,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val startDestination = destination
    val context = LocalContext.current

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
                selectedTabIndex = destination.ordinal,
                indicator = {
                    TabRowDefaults.SecondaryIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .tabIndicatorLayout { measurable: Measurable,
                                                  constraints: Constraints,
                                                  tabPositions: List<TabPosition> ->

                                val start = tabPositions[destination.ordinal].left.toPx().toInt()
                                layout(constraints.maxWidth , constraints.constrainHeight(4.dp.toPx().toInt())){
                                    measurable.measure(constraints).place(start, 0)
                                }
                            }
                            .padding(
                                start = if (destination == Destination.SCANNED) 16.dp else 0.dp,
                                end = if (destination == Destination.CREATED) 16.dp else 0.dp
                            )
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = destination.ordinal == index,
                        onClick = {
                            navController.navigate(route = destination.name)
                            onAction(HistoryAction.OnSwitchHistory(destination))
                        },
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        text = {
                            Text(
                                text = destination.label.asString(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    letterSpacing = (-0.01).em
                                )
                            )
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
                            qrCodeUis = qrCodeUis,
                            onAction = onAction,
                            modifier = Modifier.padding(
                                top = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
                            )
                        )
                    }
                }
            }
            if (qrCodeUis.isEmpty()) {
                Text(
                    text = stringResource(R.string.history_is_empty),
                    style = MaterialTheme.typography.labelLarge.copy(
                        letterSpacing = (-0.01).em
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
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
            if (showBottomSheet) {
                HistoryBottomSheet(
                    onDismissRequest = { onAction(HistoryAction.OnDismissBottomSheet) },
                    onShareClick = { onAction(HistoryAction.OnShareClick(context)) },
                    onDeleteClick = { onAction(HistoryAction.OnDeleteClick) }
                )
            }
        }

    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    QRCraftTheme {
        Surface {
            val fakeState = HistoryState(
                qrCodes = fakeQrCodes,
                showBottomSheet = false
            )

            HistoryScreen(
                qrCodeUis = fakeState.qrCodes,
                destination = fakeState.destination,
                showBottomSheet = fakeState.showBottomSheet,
                onAction = {}
            )
        }
    }
}