package com.example.qrcraft.scanner.data.result

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.qrcraft.scanner.domain.ImageStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

class AndroidImageStorage(
    private val context: Context
) : ImageStorage {

    override suspend fun saveTemporary(bitmap: Bitmap): Uri {
        cleanUpTemporaryFile()
        val id = UUID.randomUUID().toString()
        val file = File(
            context.cacheDir,
            "${ImageStorage.IMAGE_FILE_PREFIX}-$id.${ImageStorage.IMAGE_FILE_EXTENSION}"
        )

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

    override suspend fun savePersistent(uri: Uri, fileName: String) = withContext(Dispatchers.IO) {
        try {
            val contextResolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, generateSavedFile(fileName))
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI

            val downloadsUri = contextResolver.insert(collection, values) ?: return@withContext

            contextResolver.openOutputStream(downloadsUri).use { outputStream ->
                val inputStream = contextResolver.openInputStream(uri)
                inputStream?.copyTo(outputStream!!)
                outputStream?.flush()
            }

            values.clear()
            values.put(MediaStore.MediaColumns.IS_PENDING, 0)
            contextResolver.update(downloadsUri, values, null, null)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun cleanUpTemporaryFile() {
        withContext(Dispatchers.IO) {
            context.cacheDir.listFiles()
                ?.filter { it.name.startsWith(ImageStorage.IMAGE_FILE_PREFIX) }
                ?.forEach { file ->
                    file.delete()
                }
        }
    }

    private fun generateSavedFile(fileName: String): String {
        val timestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString()
        return "$fileName-$timestamp.png"
    }
}