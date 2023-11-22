package com.book.auto.domain

import com.book.auto.data.remote.reqres.BasicResponse
import com.book.auto.data.remote.reqres.CountRequest
import com.book.auto.data.remote.reqres.VehiclesResponse
import retrofit2.Response

interface BVApi {
    suspend fun getAllVehicle(): Response<VehiclesResponse>
    suspend fun incAutoSelectCount(request: CountRequest): Response<BasicResponse>
    suspend fun incAutoCallCount(request: CountRequest): Response<BasicResponse>
}