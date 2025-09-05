package com.example.qrcraft.scanner.presentation.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.qrcraft.R
import com.example.qrcraft.core.presentation.designsystem.navbars.ScannerBottomNavigation
import com.example.qrcraft.core.presentation.util.ObserveAsEvents
import com.example.qrcraft.core.presentation.util.SnackBarController
import com.example.qrcraft.core.presentation.util.toString
import com.example.qrcraft.scanner.data.mapper.toAndroidRect
import com.example.qrcraft.scanner.domain.models.ErrorResponse
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.ResponseType
import com.example.qrcraft.scanner.presentation.scanner.components.CameraPermissionDialog
import com.example.qrcraft.scanner.presentation.scanner.components.CameraPreview
import com.example.qrcraft.scanner.presentation.scanner.components.ErrorDialog
import com.example.qrcraft.scanner.presentation.scanner.components.LoadingIndicator
import com.example.qrcraft.scanner.presentation.scanner.components.ScannerOverlay
import com.example.qrcraft.scanner.presentation.scanner.components.ScannerSnackBar
import com.example.qrcraft.scanner.presentation.util.SetStatusBarIconsColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun ScannerScreenRoot(
    onScanResult: (QrCode) -> Unit,
    onCloseApp: () -> Unit,
    onCreateQRCodeClick: () -> Unit,
    onScanHistoryClick: () -> Unit,
    viewModel: ScannerViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val applicationContext = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = LocalActivity.current as Activity

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted)
            viewModel.onAction(ScannerAction.OnCameraPermissionDenied)
        else
            viewModel.onAction(ScannerAction.OnCameraPermissionGranted)
    }

    val cameraController = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(
                LifecycleCameraController.IMAGE_ANALYSIS
            )
        }
    }
    cameraController.bindToLifecycle(lifecycleOwner)

    SetStatusBarIconsColor(darkIcons = false)

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        )
            permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ScannerEvent.OnResult -> onScanResult(event.qrCode)
        }
    }

    ScannerScreen(
        isLoading = state.isLoading,
        onAction = {
            when (it) {
                ScannerAction.OnCreateQrClick -> onCreateQRCodeClick()
                ScannerAction.OnHistoryScanClick -> onScanHistoryClick()
                else -> viewModel.onAction(it)
            }
        },
        cameraController = cameraController
    )

    if (state.showPermissionDialog) {
        CameraPermissionDialog(
            onClose = onCloseApp,
            onGrantAccess = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
                viewModel.onAction(ScannerAction.OnGrantAccessClick)
            }
        )
    }
}

@Composable
fun ScannerScreen(
    isLoading: Boolean,
    onAction: (ScannerAction) -> Unit,
    cameraController: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onAction(
            ScannerAction.OnStartScanning(
                context = context,
                cameraController = cameraController
            )
        )
    }

    ObserveAsEvents(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            when (event.message) {
                is ErrorResponse -> {
                    when (event.message.type) {
                        ResponseType.DIALOG -> {
                            showErrorDialog = true
                            delay(3000)
                            showErrorDialog = false
                        }

                        ResponseType.SNACKBAR -> {
                            snackbarHostState.showSnackbar(
                                message = event.message.message.toString(context),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ScannerSnackBar(
                    snackbarData = data
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {
            CameraPreview(
                controller = cameraController,
                modifier = Modifier.fillMaxSize()
            )
            ScannerOverlay(
                onOverlayDrawn = { rect ->
                    onAction(ScannerAction.OnOverlayDrawn(rect.toAndroidRect()))
                },
                modifier = Modifier.fillMaxSize()
            )
            ScannerBottomNavigation(
                onHistoryClick = {
                    onAction(ScannerAction.OnHistoryScanClick)
                },
                onScanClick = {

                },
                onCreateQrClick = {
                    onAction(ScannerAction.OnCreateQrClick)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp)
            )
            if (isLoading)
                LoadingIndicator()
        }
    }

    if (showErrorDialog)
        ErrorDialog(
            message = stringResource(R.string.no_qr_codes_found),
            onDismiss = {
                showErrorDialog = false
            }
        )
}
