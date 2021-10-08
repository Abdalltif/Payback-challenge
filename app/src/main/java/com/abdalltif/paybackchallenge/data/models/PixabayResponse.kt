package com.abdalltif.paybackchallenge.data.models


import com.google.gson.annotations.SerializedName


data class PixabayResponse(
    @SerializedName("hits")
    val hits: List<Photo>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)