package com.abdalltif.paybackchallenge.repository

import com.abdalltif.check24.datasource.api.PixabayApi
import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import com.abdalltif.paybackchallenge.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val pixabayApi: PixabayApi
    ) {

    suspend fun searchPhotos(query: String): Response<PixabayResponse> {
        return pixabayApi.searchPhotos(Constants.API_KEY, query)
    }
}