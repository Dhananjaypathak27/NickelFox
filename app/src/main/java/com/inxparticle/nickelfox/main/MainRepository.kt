package com.inxparticle.nickelfox.main

import com.inxparticle.nickelfox.data.model.JsonPlaceHolderItemResponse
import com.inxparticle.nickelfox.utils.Resource


interface MainRepository {

    suspend fun getData(): Resource<JsonPlaceHolderItemResponse>
}