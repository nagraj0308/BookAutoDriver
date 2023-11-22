package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.data.remote.reqres.HousesResponse
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

    @GET("api/getAllVehicleAdminV1")
    suspend fun getAllVehicleAdmin(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String
    ): Response<VehiclesResponse>

    @GET("api/getAllHouseAdminV1")
    suspend fun getAllHouseAdmin(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String
    ): Response<HousesResponse>

    @GET("api/changeAutoStatusV1")
    suspend fun changeAutoStatusV1(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

    @GET("api/changeVehicleStatusV1")
    suspend fun changeVehicleStatusV1(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

    @GET("api/changeHouseStatus")
    suspend fun changeHouseStatus(
        @Query("password") password: String,
        @Query("verificationState") verificationState: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

    @GET("api/changeAutoAdminRemark")
    suspend fun changeAutoAdminRemark(
        @Query("password") password: String,
        @Query("adminRemark") adminRemark: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>


    @GET("api/changeVehicleAdminRemark")
    suspend fun changeVehicleAdminRemark(
        @Query("password") password: String,
        @Query("adminRemark") adminRemark: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

    @GET("api/changeHouseAdminRemark")
    suspend fun changeHouseAdminRemark(
        @Query("password") password: String,
        @Query("adminRemark") adminRemark: String,
        @Query("vId") vId: String
    ): Response<BasicResponse>

}