package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface BVService {
    @Headers("Content-Type: application/json")
    @GET("api/getAllAutoAdmin/")
    suspend fun getAllAutoAdmin(@Query("password") password: String): Response<AutosResponse>

}