package com.example.qrcraft.scanner.domain

import android.graphics.Bitmap
import android.net.Uri

interface ImageStorage {
    suspend fun saveTemporary(bitmap: Bitmap): Uri
    suspend fun  savePersistent(uri: Uri, fileName: String)
    suspend fun cleanUpTemporaryFile()

    companion object {
        const val IMAGE_FILE_EXTENSION = "png"
        const val IMAGE_FILE_PREFIX = "temp-qrcode"
    }
}


