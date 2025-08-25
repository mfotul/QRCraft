package com.example.qrcraft.scanner.domain.models

import com.example.qrcraft.core.domain.util.Message

data class ErrorResponse(
    val message: Message,
    val type: ResponseType
)

enum class ResponseType {
    SNACKBAR,
    DIALOG
}