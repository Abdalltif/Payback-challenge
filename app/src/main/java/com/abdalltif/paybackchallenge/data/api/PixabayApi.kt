package com.abdalltif.check24.datasource.api

import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun searchPhotos(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") image_type: String,
        @Query("pretty") pretty: Boolean,
    ): Response<PixabayResponse>

}