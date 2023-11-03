package com.book.auto.data.remote

import com.book.auto.data.remote.reqres.VehiclesResponse
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST


interface BVService {

    @Headers("Content-Type: application/json")
    @POST("api/getAllActiveAutoV1/")
    suspend fun getAllVehicle(): Response<VehiclesResponse>

}