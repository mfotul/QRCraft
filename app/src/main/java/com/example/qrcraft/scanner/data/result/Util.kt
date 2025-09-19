package com.example.qrcraft.scanner.data.result

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

fun generateQrCodeBitmap(
    content: String,
    size: Int = 512,
    borderSize: Int = 20
): Bitmap {
    val qrCodeWriter = QRCodeWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 0)
    val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints)

    val finalSize = size + borderSize * 2
    val pixels = IntArray(finalSize * finalSize) { Color.WHITE }

    for (y in 0 until size) {
        for (x in 0 until size) {
            if (bitMatrix[x, y]) {
                val newX = x + borderSize
                val newY = y + borderSize
                pixels[newY * finalSize + newX] = Color.BLACK
            }
        }
    }

    return createBitmap(finalSize, finalSize).also {
        it.setPixels(pixels, 0, finalSize, 0, 0, finalSize, finalSize)
    }
}