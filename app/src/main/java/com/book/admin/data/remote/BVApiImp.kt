package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.data.remote.reqres.HomestaysResponse
import com.book.admin.data.remote.reqres.HousesResponse
import com.book.admin.data.remote.reqres.VehiclesResponse
import com.book.admin.domain.BVApi
import retrofit2.Response
import javax.inject.Inject


class BVApiImp @Inject constructor(
    private val service: BVService,
) : BVApi {

    override suspend fun getAllAutoAdmin(code: String, password: String): Response<AutosResponse> {
        return service.getAllAutoAdmin(password, code)
    }

    override suspend fun getAllVehicleAdmin(
        code: String, password: String
    ): Response<VehiclesResponse> {
        return service.getAllVehicleAdmin(password, code)
    }

    override suspend fun getAllHouseAdmin(
        password: String,
        verificationState: String
    ): Response<HousesResponse> {
        return service.getAllHouseAdmin(password, verificationState)
    }

    override suspend fun getAllHomestayAdmin(
        password: String,
        verificationState: String
    ): Response<HomestaysResponse> {
        return service.getAllHomestayAdmin(password, verificationState)
    }

    override suspend fun changeAutoStatusV1(
        password: String, verificationState: String, vId: String
    ): Response<BasicResponse> {
        return service.changeAutoStatusV1(password, verificationState, vId)
    }

    override suspend fun changeVehicleStatusV1(
        password: String, verificationState: String, vId: String
    ): Response<BasicResponse> {
        return service.changeVehicleStatusV1(password, verificationState, vId)
    }

    override suspend fun changeHouseStatus(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse> {
        return service.changeHouseStatus(password, verificationState, vId)
    }

    override suspend fun changeHomestayStatus(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse> {
        return service.changeHomestayStatus(password, verificationState, vId)
    }

    override suspend fun changeAutoAdminRemark(
        password: String, adminRemark: String, vId: String
    ): Response<BasicResponse> {
        return service.changeAutoAdminRemark(password, adminRemark, vId)
    }


    override suspend fun changeVehicleAdminRemark(
        password: String, adminRemark: String, vId: String
    ): Response<BasicResponse> {
        return service.changeVehicleAdminRemark(password, adminRemark, vId)
    }

    override suspend fun changeHouseAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse> {
        return service.changeHouseAdminRemark(password, adminRemark, vId)
    }

    override suspend fun changeHomestayAdminRemark(
        password: String,
        adminRemark: String,
        vId: String
    ): Response<BasicResponse> {
        return service.changeHomestayAdminRemark(password, adminRemark, vId)
    }


}