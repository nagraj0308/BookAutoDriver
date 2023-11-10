package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.data.remote.reqres.VehiclesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BVService {
    @GET("api/getAllAutoAdminV1")
    suspend fun getAllAutoAdmin(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String
    ): Response<AutosResponse>

    @GET("api/changeAutoStatusV1")
    suspend fun changeAutoStatusV1(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>


    @GET("api/getAllVehicleAdminV1")
    suspend fun getAllVehicleAdmin(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String
    ): Response<VehiclesResponse>

    @GET("api/changeVehicleStatusV1")
    suspend fun changeVehicleStatusV1(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

}