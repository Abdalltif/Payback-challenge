package com.abdalltif.paybackchallenge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.abdalltif.check24.datasource.api.PixabayApi
import com.abdalltif.paybackchallenge.data.local.PhotoDao
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import com.abdalltif.paybackchallenge.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class MainRoomRepository @Inject constructor(
        private val photoDao: PhotoDao
    ) {

    suspend fun addLocalPhoto(photo: Photo) {
        photoDao.addPhoto(photo)
    }

    suspend fun readAllLocalPhotos(): List<Photo> {
        return photoDao.readAllPhotos()
    }

    suspend fun searchLocalPhotos(query: String): List<Photo> {
        return photoDao.searchPhotos(query)
    }

}