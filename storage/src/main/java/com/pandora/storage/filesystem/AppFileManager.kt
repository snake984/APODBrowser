package com.pandora.storage.filesystem

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

interface FileManager {
    suspend fun saveImage(data: ByteArray, filename: String)

    suspend fun deleteImage(filename: String)

    fun getImage(filename: String): Flow<ByteArray?>
}

internal class AppFileManager(private val appFilesDir: File) : FileManager {
    override suspend fun saveImage(data: ByteArray, filename: String) {
        val imagesDir = File(appFilesDir, IMAGES_DIR_PATH)

        if (!imagesDir.exists()) {
            imagesDir.mkdir()
        }

        val newImage = File(imagesDir, filename)
        newImage.writeBytes(data)
    }

    override suspend fun deleteImage(filename: String) {
        val imagesDir = File(appFilesDir, IMAGES_DIR_PATH)

        if (imagesDir.exists()) {
            val imageToDelete = File(imagesDir, filename)
            imageToDelete.delete()
        }
    }

    override fun getImage(filename: String): Flow<ByteArray?> {
        val imagesDir = File(appFilesDir, IMAGES_DIR_PATH)

        val image = File(imagesDir, filename)
        return flow {
            if (image.exists()) {
                emit(image.readBytes())
            } else {
                emit(null)
            }
        }
    }

    companion object {
        const val IMAGES_DIR_PATH = "/images"
    }
}