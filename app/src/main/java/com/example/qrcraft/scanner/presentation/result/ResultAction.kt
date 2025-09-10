package com.example.qrcraft.scanner.presentation.result

import android.content.Context

sealed interface ResultAction {
    data object OnBackClick: ResultAction
    data class OnShareClick(val context: Context): ResultAction
    data class OnCopyClick(val context: Context): ResultAction
    data class OnLabelChange(val label: String): ResultAction
    data object OnTextClick: ResultAction
}