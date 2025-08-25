package com.example.qrcraft.scanner.presentation.scanner

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.qrcraft.R
import com.example.qrcraft.core.presentation.util.ObserveAsEvents
import com.example.qrcraft.core.presentation.util.SnackBarController
import com.example.qrcraft.core.presentation.util.toString
import com.example.qrcraft.scanner.data.mapper.toAndroidRect
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.domain.models.ErrorResponse
import com.example.qrcraft.scanner.domain.models.ResponseType
import com.example.qrcraft.scanner.presentation.scanner.components.CameraPermissionDialog
import com.example.qrcraft.scanner.presentation.scanner.components.CameraPreview
import com.example.qrcraft.scanner.presentation.scanner.components.ErrorDialog
import com.example.qrcraft.scanner.presentation.scanner.components.LoadingIndicator
import com.example.qrcraft.scanner.presentation.scanner.components.ScannerOverlay
import com.example.qrcraft.scanner.presentation.scanner.components.ScannerSnackBar
import com.example.qrcraft.scanner.presentation.scanner.components.SetStatusBarIconsColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScannerScreenRoot(
    onScanResult: (BarcodeData) -> Unit,
    viewModel: ScannerViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val applicationContext = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

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



    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        )
            permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is ScannerEvent.OnResult -> onScanResult(event.barcodeData)
        }
    }

    ScannerScreen(
        isLoading = state.isLoading,
        onAction = viewModel::onAction,
        cameraController = cameraController
    )

    if (state.showPermissionDialog) {
        CameraPermissionDialog(
            onClose = { (context as? Activity)?.finish() },
            onGrantAccess = {
                viewModel.onAction(ScannerAction.OnGrantAccessClick)
                permissionLauncher.launch(Manifest.permission.CAMERA)
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
        onAction(ScannerAction.OnStartScanning(context =  context, cameraController = cameraController))
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
        contentWindowInsets = WindowInsets.waterfall,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
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
