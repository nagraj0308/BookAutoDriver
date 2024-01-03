package com.book.homestay.data.remote

import com.book.homestay.data.remote.reqres.BasicResponse
import com.book.homestay.data.remote.reqres.DeleteRequest
import com.book.homestay.data.remote.reqres.GetHouseByIdRequest
import com.book.homestay.data.remote.reqres.GetHouseRequest
import com.book.homestay.data.remote.reqres.HouseRequest
import com.book.homestay.data.remote.reqres.HouseResponse
import com.book.homestay.data.remote.reqres.HousesResponse
import com.book.homestay.data.remote.reqres.Img1Request
import com.book.homestay.data.remote.reqres.Img2Request
import com.book.homestay.data.remote.reqres.Img3Request
import com.book.homestay.data.remote.reqres.Img4Request
import com.book.homestay.domain.Api
import retrofit2.Response
import javax.inject.Inject


class ApiImp @Inject constructor(
    private val service: Service
) : Api {

    override suspend fun getAllActiveHouse(request: GetHouseRequest?): Response<HousesResponse> {
        return service.getAllActiveHouse(request)
    }

    override suspend fun insertHouse(request: HouseRequest?): Response<HouseResponse> {
        return service.insertHouse(request)
    }

    override suspend fun updateHouse(request: HouseRequest?): Response<HouseResponse> {
        return service.updateHouse(request)
    }

    override suspend fun updateImg1(request: Img1Request?): Response<BasicResponse> {
        return service.updateImg1(request)
    }

    override suspend fun updateImg2(request: Img2Request?): Response<BasicResponse> {
        return service.updateImg2(request)
    }

    override suspend fun updateImg3(request: Img3Request?): Response<BasicResponse> {
        return service.updateImg3(request)
    }

    override suspend fun updateImg4(request: Img4Request?): Response<BasicResponse> {
        return service.updateImg4(request)
    }

    override suspend fun getHouseById(request: GetHouseByIdRequest?): Response<HouseResponse> {
        return service.getHouseByGmailId(request)
    }

    override suspend fun deleteHouseById(request: DeleteRequest?): Response<BasicResponse> {
        return service.deleteHouseById(request)
    }

}