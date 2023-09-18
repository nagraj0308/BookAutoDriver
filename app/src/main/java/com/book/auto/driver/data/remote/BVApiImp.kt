package com.book.auto.driver.data.remote

import com.book.auto.driver.data.remote.reqres.BasicResponse
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.data.remote.reqres.VehicleResponse
import com.book.auto.driver.data.remote.reqres.VehiclesResponse
import com.book.auto.driver.domain.BVApi
import retrofit2.Response
import javax.inject.Inject


class BVApiImp @Inject constructor(
    private val service: BVService,
) : BVApi {

    override suspend fun insertVehicle(request: VehicleRequest?): Response<VehicleResponse> {
        return service.insertVehicle(request)
    }

    override suspend fun updateVehicle(request: VehicleRequest?): Response<VehicleResponse> {
        return service.updateVehicle(request)
    }


    override suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehiclesResponse> {
        return service.getVehicleByGmailId(request)
    }

    override suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse> {
        return service.deleteVehicleById(request)
    }

}