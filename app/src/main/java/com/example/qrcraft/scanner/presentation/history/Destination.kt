package com.example.qrcraft.scanner.presentation.history

import com.example.qrcraft.R
import com.example.qrcraft.core.presentation.util.UiText

enum class Destination(
    val label: UiText
) {
    SCANNED(
        label = UiText.StringResource(R.string.scanned)
    ),
    GENERATED(
        label = UiText.StringResource(R.string.generated)
    )
}