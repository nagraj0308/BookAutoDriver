package com.book.homestay.data.remote

import com.book.homestay.data.remote.reqres.BasicResponse
import com.book.homestay.data.remote.reqres.DeleteRequest
import com.book.homestay.data.remote.reqres.GetHomestayByIdRequest
import com.book.homestay.data.remote.reqres.GetHomestayRequest
import com.book.homestay.data.remote.reqres.HomestayRequest
import com.book.homestay.data.remote.reqres.HomestayResponse
import com.book.homestay.data.remote.reqres.HomestaysResponse
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

    override suspend fun getAllActiveHomestay(request: GetHomestayRequest?): Response<HomestaysResponse> {
        return service.getAllActiveHomestay(request)
    }

    override suspend fun insertHomestay(request: HomestayRequest?): Response<HomestayResponse> {
        return service.insertHomestay(request)
    }

    override suspend fun updateHomestay(request: HomestayRequest?): Response<HomestayResponse> {
        return service.updateHomestay(request)
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

    override suspend fun getHomestayById(request: GetHomestayByIdRequest?): Response<HomestayResponse> {
        return service.getHomestayByGmailId(request)
    }

    override suspend fun deleteHomestayById(request: DeleteRequest?): Response<BasicResponse> {
        return service.deleteHomestayById(request)
    }

}