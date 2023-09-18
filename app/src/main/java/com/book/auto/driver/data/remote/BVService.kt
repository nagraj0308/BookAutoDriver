package com.book.auto.driver.data.remote

import com.book.auto.driver.data.remote.reqres.BasicResponse
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.data.remote.reqres.VehicleResponse
import com.book.auto.driver.data.remote.reqres.VehiclesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface BVService {
    @Headers("Content-Type: application/json")
    @POST("api/insertVehicle/")
    suspend fun insertVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateVehicle/")
    suspend fun updateVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/getVehicleByGmailId/")
    suspend fun getVehicleByGmailId(@Body request: GetVehicleByGmailIdRequest?): Response<VehiclesResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteVehicleById/")
    suspend fun deleteVehicleById(@Body request: DeleteVehicleRequest?): Response<BasicResponse>

}