package com.inxparticle.nickelfox.data

import com.inxparticle.nickelfox.data.model.JsonPlaceHolderItemResponse
import retrofit2.Response
import retrofit2.http.GET

interface NickelFoxApi {

    @GET("posts/1")
    suspend fun getData(
    ): Response<JsonPlaceHolderItemResponse>

}