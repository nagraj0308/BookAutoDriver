package com.book.auto.driver.domain

import com.book.auto.driver.data.remote.reqres.BasicResponse
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.data.remote.reqres.VehicleResponse
import com.book.auto.driver.data.remote.reqres.VehiclesResponse
import retrofit2.Response

interface BVApi {
    suspend fun insertVehicle(request: VehicleRequest?): Response<VehicleResponse>

    suspend fun updateVehicle(request: VehicleRequest?): Response<VehicleResponse>

    suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehiclesResponse>

    suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse>

}