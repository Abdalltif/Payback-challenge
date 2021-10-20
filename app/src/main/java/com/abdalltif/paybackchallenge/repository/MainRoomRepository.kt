package com.abdalltif.paybackchallenge.repository

import com.abdalltif.paybackchallenge.data.local.PhotoDao
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import retrofit2.Response
import javax.inject.Inject

class MainRoomRepository @Inject constructor(
        private val photoDao: PhotoDao
    ) : IPhotoRepository {

    suspend fun addLocalPhoto(photo: Photo) {
        photoDao.addPhoto(photo)
    }

    suspend fun readAllLocalPhotos(): List<Photo> {
        return photoDao.readAllPhotos()
    }

    suspend fun searchLocalPhotos(query: String): List<Photo> {
        return photoDao.searchPhotos(query)
    }

    override suspend fun searchPhotos(query: String): Response<PixabayResponse> {
        TODO("Not yet implemented")
    }

}