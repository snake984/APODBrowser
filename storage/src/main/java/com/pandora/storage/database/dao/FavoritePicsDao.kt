package com.pandora.storage.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pandora.storage.database.entities.FavoritePicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePicsDao {
    @Query("SELECT * FROM favorite_pics")
    fun getAll(): Flow<List<FavoritePicEntity>>

    @Query("SELECT * FROM favorite_pics WHERE id IN (:id)")
    fun getById(id: Long): Flow<List<FavoritePicEntity>>

    @Query("SELECT * FROM favorite_pics WHERE title LIKE :title")
    fun getByTitle(title: String): Flow<List<FavoritePicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pics: List<FavoritePicEntity>)

    @Delete
    suspend fun delete(pic: FavoritePicEntity)
}