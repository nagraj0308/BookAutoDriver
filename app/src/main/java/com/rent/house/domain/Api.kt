package com.rent.house.domain

import com.rent.house.data.remote.reqres.BasicResponse
import com.rent.house.data.remote.reqres.DeleteVehicleRequest
import com.rent.house.data.remote.reqres.GetVehicleByGmailIdRequest
import com.rent.house.data.remote.reqres.GetVehicleRequest
import com.rent.house.data.remote.reqres.VehicleRequest
import com.rent.house.data.remote.reqres.VehicleResponse
import com.rent.house.data.remote.reqres.VehiclesResponse
import retrofit2.Response

interface Api {

    suspend fun getAllVehicle(request: GetVehicleRequest?): Response<VehiclesResponse>
    suspend fun insertVehicle(request: VehicleRequest?): Response<VehicleResponse>
    suspend fun updateVehicle(request: VehicleRequest?): Response<VehicleResponse>
    suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehicleResponse>
    suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse>

}