package com.abdalltif.paybackchallenge.repository

import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import retrofit2.Response

interface IPhotoRepository {
    suspend fun searchPhotos(query: String): Response<PixabayResponse>
}