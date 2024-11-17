package com.pandora.storage.filesystem

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import androidx.core.net.toUri

internal class MediaStoreFileManager(private val context: Context) : FileManager {

    override suspend fun saveImage(data: ByteArray, filename: String): String? = withContext(Dispatchers.IO) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val apodDir = java.io.File(downloadsDir, "APODBrowser")
            if (!apodDir.exists()) {
                apodDir.mkdirs()
            }
            val file = java.io.File(apodDir, filename)
            try {
                file.writeBytes(data)
                return@withContext Uri.fromFile(file).toString()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

        val resolver = context.contentResolver
        
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/APODBrowser")
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            // This path won't be reached due to the check at the top, but satisfies lint
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val uri = try {
            resolver.insert(collection, contentValues) ?: return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }

        resolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(data)
        }

        contentValues.clear()
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)

        uri.toString()
    }

    override suspend fun deleteImage(uriString: String) {
        withContext(Dispatchers.IO) {
            val uri = uriString.toUri()
            if (uri.scheme == "file") {
                val file = java.io.File(uri.path ?: return@withContext)
                if (file.exists()) {
                    file.delete()
                }
            } else {
                val resolver = context.contentResolver
                try {
                    resolver.delete(uri, null, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun getImage(uriString: String): Flow<ByteArray?> = flow {
        val uri = uriString.toUri()
        if (uri.scheme == "file") {
            val file = java.io.File(uri.path ?: "")
            if (file.exists()) {
                emit(file.readBytes())
            } else {
                emit(null)
            }
        } else {
            val resolver = context.contentResolver
            try {
                resolver.openInputStream(uri)?.use { inputStream ->
                    emit(inputStream.readBytes())
                } ?: emit(null)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(null)
            }
        }
    }
}
