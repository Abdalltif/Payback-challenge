package com.abdalltif.paybackchallenge.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdalltif.paybackchallenge.data.models.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPhoto(photo: Photo)

    @Query("SELECT * FROM photo_table ORDER BY id ASC")
    suspend fun readAllPhotos(): List<Photo>

    @Query("SELECT * FROM photo_table WHERE tags LIKE :query")
    suspend fun searchPhotos(query: String): List<Photo>

}