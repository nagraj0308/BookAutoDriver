package com.book.auto.driver.data.remote

import com.book.auto.driver.data.remote.reqres.BasicResponse
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.data.remote.reqres.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface BVService {
    @Headers("Content-Type: application/json")
    @POST("api/insertAuto/")
    suspend fun insertVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateAuto/")
    suspend fun updateVehicle(@Body request: VehicleRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/getAutoByGmailId/")
    suspend fun getVehicleByGmailId(@Body request: GetVehicleByGmailIdRequest?): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteAutoById/")
    suspend fun deleteVehicleById(@Body request: DeleteVehicleRequest?): Response<BasicResponse>

}