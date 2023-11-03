package com.book.auto.domain

import com.book.auto.data.remote.reqres.VehiclesResponse
import retrofit2.Response

interface BVApi {
    suspend fun getAllVehicle(): Response<VehiclesResponse>

}