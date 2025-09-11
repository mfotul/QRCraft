package com.example.qrcraft.scanner.presentation.result

import android.graphics.Bitmap
import androidx.compose.ui.text.input.TextFieldValue
import com.example.qrcraft.scanner.domain.models.QrCode

data class ResultState(
    val qrCodeUiData: QrCodeUiData? = null,
    val label: TextFieldValue? = null,
    val isEditMode: Boolean = false
)

data class QrCodeUiData(
    val qrCode: QrCode,
    val qrCodeBitmap: Bitmap
)
