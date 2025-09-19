package com.example.qrcraft.scanner.presentation.result

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue

sealed interface ResultAction {
    data object OnBackClick : ResultAction
    data class OnShareClick(val context: Context) : ResultAction
    data class OnCopyClick(val context: Context) : ResultAction
    data object OnDownloadClick : ResultAction
    data class OnLabelChange(val label: TextFieldValue) : ResultAction
    data object OnTextClick : ResultAction
    data object OnFavoriteClick : ResultAction
}