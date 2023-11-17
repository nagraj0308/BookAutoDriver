package com.book.admin.domain

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.data.remote.reqres.VehiclesResponse
import retrofit2.Response
import retrofit2.http.Query


interface BVApi {
    suspend fun getAllAutoAdmin(code: String, password: String): Response<AutosResponse>

    suspend fun changeAutoStatusV1(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeAutoAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun getAllVehicleAdmin(code: String, password: String): Response<VehiclesResponse>

    suspend fun changeVehicleStatusV1(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeVehicleAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>


}