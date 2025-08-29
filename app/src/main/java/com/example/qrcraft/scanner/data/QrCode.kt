package com.example.qrcraft.scanner.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object QrCode {
    fun generateQrCodeBitmap(content: String, size: Int = 512, borderSize: Int = 20): Bitmap {
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

    suspend fun saveBitmap(context: Context, bitmap: Bitmap): Uri {
        val id = UUID.randomUUID().toString()
        val file = File(context.cacheDir, "qrcode-$id.png")

        return withContext(Dispatchers.IO) {
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        }
    }
}