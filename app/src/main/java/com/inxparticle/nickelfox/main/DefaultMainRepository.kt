package com.inxparticle.nickelfox.main

import com.inxparticle.nickelfox.data.NickelFoxApi
import com.inxparticle.nickelfox.data.model.JsonPlaceHolderItemResponse
import com.inxparticle.nickelfox.utils.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: NickelFoxApi
) : MainRepository {
    override suspend fun getData(): Resource<JsonPlaceHolderItemResponse> {
        return try{
            val response = api.getData()
            val result = response.body()
            if(response.isSuccessful && result!=null){
                Resource.Success(result)
            }
            else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}