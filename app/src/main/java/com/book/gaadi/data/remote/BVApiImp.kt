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
import com.book.gaadi.domain.BVApi
import retrofit2.Response
import javax.inject.Inject


class BVApiImp @Inject constructor(
    private val service: BVService
) : BVApi {

    override suspend fun getAllVehicle(request: GetVehicleRequest?): Response<VehiclesResponse> {
        return service.getAllVehicleV1(request)
    }

    override suspend fun insertVehicle(request: VehicleRequest?): Response<VehicleResponse> {
        return service.insertVehicle(request)
    }

    override suspend fun updateVehicle(request: VehicleRequest?): Response<VehicleResponse> {
        return service.updateVehicle(request)
    }

    override suspend fun updateVehicleLocation(request: VehicleLocationRequest?): Response<BasicResponse> {
        return service.updateVehicleLocation(request)
    }

    override suspend fun updateAutoActiveStatus(request: VerificationStatusRequest?): Response<BasicResponse> {
        return service.updateAutoActiveStatus(request)
    }


    override suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehicleResponse> {
        return service.getVehicleByGmailId(request)
    }

    override suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse> {
        return service.deleteVehicleById(request)
    }

}