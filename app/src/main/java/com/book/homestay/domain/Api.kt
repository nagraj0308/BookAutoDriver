package com.book.homestay.domain

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
import retrofit2.Response

interface Api {

    suspend fun getAllActiveHomestay(request: GetHomestayRequest?): Response<HomestaysResponse>
    suspend fun insertHomestay(request: HomestayRequest?): Response<HomestayResponse>
    suspend fun updateHomestay(request: HomestayRequest?): Response<HomestayResponse>
    suspend fun updateImg1(request: Img1Request?): Response<BasicResponse>
    suspend fun updateImg2(request: Img2Request?): Response<BasicResponse>
    suspend fun updateImg3(request: Img3Request?): Response<BasicResponse>
    suspend fun updateImg4(request: Img4Request?): Response<BasicResponse>
    suspend fun getHomestayById(request: GetHomestayByIdRequest?): Response<HomestayResponse>
    suspend fun deleteHomestayById(request: DeleteRequest?): Response<BasicResponse>

}