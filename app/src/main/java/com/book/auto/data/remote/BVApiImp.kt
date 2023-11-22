package com.book.auto.data.remote

import com.book.auto.data.remote.reqres.BasicResponse
import com.book.auto.data.remote.reqres.CountRequest
import com.book.auto.data.remote.reqres.VehiclesResponse
import com.book.auto.domain.BVApi
import retrofit2.Response
import javax.inject.Inject


class BVApiImp @Inject constructor(
    private val service: BVService,
) : BVApi {

    override suspend fun getAllVehicle(): Response<VehiclesResponse> {
        return service.getAllVehicle()
    }

    override suspend fun incAutoSelectCount(request: CountRequest): Response<BasicResponse> {
        return service.incAutoSelectCount(request)
    }

    override suspend fun incAutoCallCount(request: CountRequest): Response<BasicResponse> {
        return service.incAutoCallCount(request)
    }

}