package com.example.newfunctest_recycle.network

import com.example.newfunctest_recycle.models.DRContainer
import com.example.newfunctest_recycle.models.DataResponse
import com.example.newfunctest_recycle.models.DataResponse2
import retrofit2.Call
import retrofit2.http.*

interface ContainerService {

   /* @Headers(
        "reqId: 4e2a13489f",
        "reqTime: 1625535642716"
    )*/
    @GET("v8/containers/get/list")
    fun getContainer(
        @Header("reqId") reqId: String,
        @Header("reqTime") reqTime: String
   ): Call<DataResponse>



    @POST("v8/containers/readyToClean/{container}")
    fun readyToCleanContainer(
        @Header("Authorization") auth: String,
        @Header("Apikey") apiKey: String,
        @Path("container") container: String
    ):Call<DRContainer>






    @GET("posts")
    fun getPost(
        @Query("userId") userId: Int,
        @Query("id") id: Int
    ):Call<DataResponse2>

}