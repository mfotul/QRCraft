package com.example.qrcraft.scanner.presentation.util

import android.content.ClipData.newPlainText
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.qrcraft.scanner.domain.models.QrCodeData

object ShareCopyQrCode {
    fun shareQrCodeWithBitmap(
        context: Context,
        qrCodeData: QrCodeData,
        uri: Uri
    ) {
        val extraText = shareExtraText(qrCodeData)
        val sentIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, extraText)
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            type = "image/png"
        }
        val shareIntent = Intent.createChooser(sentIntent, null)
        context.startActivity(shareIntent)
    }

    fun copyQrCode(context: Context, qrCodeData: QrCodeData) {
        val extraText = shareExtraText(qrCodeData)
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = newPlainText("qr_code", extraText)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun shareExtraText(qrCodeData: QrCodeData): String {
        return when (qrCodeData) {
            is QrCodeData.Contact -> "${qrCodeData.name}\n${qrCodeData.email}\n${qrCodeData.phone}"
            is QrCodeData.Geo -> "${qrCodeData.latitude}, ${qrCodeData.longitude}"
            is QrCodeData.Link -> qrCodeData.url
            is QrCodeData.Phone -> qrCodeData.number
            is QrCodeData.Text -> qrCodeData.text
            is QrCodeData.Wifi -> "${qrCodeData.ssid}\n${qrCodeData.password}\n${qrCodeData.encryptionType}"
        }
    }
}