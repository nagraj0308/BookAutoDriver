package com.rent.house.data.remote

import com.rent.house.data.remote.reqres.BasicResponse
import com.rent.house.data.remote.reqres.DeleteVehicleRequest
import com.rent.house.data.remote.reqres.GetVehicleByGmailIdRequest
import com.rent.house.data.remote.reqres.GetVehicleRequest
import com.rent.house.data.remote.reqres.VehicleRequest
import com.rent.house.data.remote.reqres.VehicleResponse
import com.rent.house.data.remote.reqres.VehiclesResponse
import com.rent.house.domain.BVApi
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


    override suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehicleResponse> {
        return service.getVehicleByGmailId(request)
    }

    override suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse> {
        return service.deleteVehicleById(request)
    }

}