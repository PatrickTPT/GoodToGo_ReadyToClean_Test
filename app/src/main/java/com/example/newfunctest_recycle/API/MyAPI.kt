package com.example.newfunctest_recycle.API

import retrofit2.Call
import retrofit2.http.GET

interface MyAPI {
    /*@POST("posts")
    Call<Post> createPost(@Body Post post)*/

    @GET("posts/")
    suspend fun getPost(): Call<Post>

    @GET("containers/get/list")
    suspend fun getList(): Call<List>
}