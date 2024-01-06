package com.book.admin.domain

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.data.remote.reqres.HomestaysResponse
import com.book.admin.data.remote.reqres.HousesResponse
import com.book.admin.data.remote.reqres.VehiclesResponse
import retrofit2.Response


interface BVApi {
    suspend fun getAllAutoAdmin(code: String, password: String): Response<AutosResponse>
    suspend fun getAllVehicleAdmin(code: String, password: String): Response<VehiclesResponse>
    suspend fun getAllHouseAdmin(
        password: String,
        verificationState: String
    ): Response<HousesResponse>

    suspend fun getAllHomestayAdmin(
        password: String,
        verificationState: String
    ): Response<HomestaysResponse>


    suspend fun changeAutoStatusV1(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeVehicleStatusV1(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeHouseStatus(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>


    suspend fun changeHomestayStatus(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeAutoAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>


    suspend fun changeVehicleAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeHouseAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>

    suspend fun changeHomestayAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse>


}