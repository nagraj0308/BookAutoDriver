package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface BVService {
    @Headers("Content-Type: application/json")
    @GET("api/getAllAutoAdminV1/")
    suspend fun getAllAutoAdmin(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String
    ): Response<AutosResponse>

    @Headers("Content-Type: application/json")
    @GET("api/changeAutoStatusV1/")
    suspend fun changeAutoStatusV1(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

}