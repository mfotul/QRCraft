package com.example.qrcraft.scanner.domain.models

import com.example.qrcraft.core.presentation.util.UiText

data class ErrorResponse(
    val uiText: UiText,
    val type: ResponseType
)

enum class ResponseType {
    SNACKBAR,
    DIALOG
}