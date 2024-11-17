package com.pandora.storage.filesystem

import kotlinx.coroutines.flow.Flow

interface FileManager {
    suspend fun saveImage(data: ByteArray, filename: String): String?

    suspend fun deleteImage(uriString: String)

    fun getImage(uriString: String): Flow<ByteArray?>
}
