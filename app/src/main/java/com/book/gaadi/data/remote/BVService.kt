package com.book.gaadi.data.remote

import com.book.gaadi.data.remote.reqres.BasicResponse
import com.book.gaadi.data.remote.reqres.DeleteVehicleRequest
import com.book.gaadi.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.gaadi.data.remote.reqres.GetVehicleRequest
import com.book.gaadi.data.remote.reqres.VehicleLocationRequest
import com.book.gaadi.data.remote.reqres.VehicleRequest
import com.book.gaadi.data.remote.reqres.VehicleResponse
import com.book.gaadi.data.remote.reqres.VehiclesResponse
import com.book.gaadi.data.remote.reqres.VerificationStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface BVService {

    @Headers("Content-Type: application/json")
    @POST("api/getAllVehicleV1/")
    suspend fun getAllVehicleV1(@Body request: GetVehicleRequest?): Response<VehiclesResponse>

    @Headers("Content-Type: application/json")
    @POST("api/insertAuto/")
    suspend fun insertVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateAuto/")
    suspend fun updateVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateAutoLocation/")
    suspend fun updateVehicleLocation(@Body request: VehicleLocationRequest?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateAutoActiveStatus/")
    suspend fun updateAutoActiveStatus(@Body request: VerificationStatusRequest?): Response<BasicResponse>


    @Headers("Content-Type: application/json")
    @POST("api/getAutoByGmailId/")
    suspend fun getVehicleByGmailId(@Body request: GetVehicleByGmailIdRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteAutoById/")
    suspend fun deleteVehicleById(@Body request: DeleteVehicleRequest?): Response<BasicResponse>

}