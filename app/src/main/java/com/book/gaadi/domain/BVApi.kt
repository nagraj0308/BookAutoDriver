package com.book.gaadi.domain

import com.book.gaadi.data.remote.reqres.BasicResponse
import com.book.gaadi.data.remote.reqres.DeleteVehicleRequest
import com.book.gaadi.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.gaadi.data.remote.reqres.VehicleLocationRequest
import com.book.gaadi.data.remote.reqres.VehicleRequest
import com.book.gaadi.data.remote.reqres.VehicleResponse
import com.book.gaadi.data.remote.reqres.VerificationStatusRequest
import retrofit2.Response

interface BVApi {
    suspend fun insertVehicle(request: VehicleRequest?): Response<VehicleResponse>

    suspend fun updateVehicle(request: VehicleRequest?): Response<VehicleResponse>

    suspend fun updateVehicleLocation(request: VehicleLocationRequest?): Response<BasicResponse>

    suspend fun updateAutoActiveStatus(request: VerificationStatusRequest?): Response<BasicResponse>

    suspend fun getVehicleByGmailId(request: GetVehicleByGmailIdRequest?): Response<VehicleResponse>

    suspend fun deleteVehicleById(request: DeleteVehicleRequest?): Response<BasicResponse>

}