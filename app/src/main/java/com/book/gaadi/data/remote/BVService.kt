package com.book.gaadi.data.remote

import com.book.gaadi.data.remote.reqres.BasicResponse
import com.book.gaadi.data.remote.reqres.DeleteVehicleRequest
import com.book.gaadi.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.gaadi.data.remote.reqres.GetVehicleRequest
import com.book.gaadi.data.remote.reqres.VehicleLocationRequest
import com.book.gaadi.data.remote.reqres.VehicleRequest
import com.book.gaadi.data.remote.reqres.VehicleResponse
import com.book.gaadi.data.remote.reqres.VehiclesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface BVService {

    @Headers("Content-Type: application/json")
    @POST("api/getAllVehicleV1/")
    suspend fun getAllVehicleV1(@Body request: GetVehicleRequest?): Response<VehiclesResponse>

    @Headers("Content-Type: application/json")
    @POST("api/insertVehicleV1/")
    suspend fun insertVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateVehicleV1/")
    suspend fun updateVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>


    @Headers("Content-Type: application/json")
    @POST("api/getVehicleByGmailId/")
    suspend fun getVehicleByGmailId(@Body request: GetVehicleByGmailIdRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteVehicleById/")
    suspend fun deleteVehicleById(@Body request: DeleteVehicleRequest?): Response<BasicResponse>

}