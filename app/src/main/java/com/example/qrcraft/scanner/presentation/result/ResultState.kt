package com.example.qrcraft.scanner.presentation.result

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.example.qrcraft.scanner.domain.models.QrCode

data class ResultState(
    val qrCode: QrCode? = null,
    val qrCodeTempUri: Uri? = null,
    val label: TextFieldValue? = null,
    val isEditMode: Boolean = false
)