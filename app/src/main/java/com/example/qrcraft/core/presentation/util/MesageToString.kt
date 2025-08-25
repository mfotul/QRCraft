package com.example.qrcraft.core.presentation.util

import android.content.Context
import com.example.qrcraft.R
import com.example.qrcraft.core.domain.util.Message

fun Message.toString(context: Context): String {
    val resId = when (this) {
        Message.NO_QR_CODES_FOUND -> R.string.no_qr_codes_found
        Message.CAMERA_PERMISSION_GRANTED -> R.string.camera_permission_granted
    }
    return context.getString(resId)
}