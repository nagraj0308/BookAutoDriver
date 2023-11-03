package com.book.auto.data.remote

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

}