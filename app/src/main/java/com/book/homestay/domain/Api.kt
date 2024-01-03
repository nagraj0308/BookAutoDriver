package com.book.homestay.domain

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
import retrofit2.Response

interface Api {

    suspend fun getAllActiveHouse(request: GetHouseRequest?): Response<HousesResponse>
    suspend fun insertHouse(request: HouseRequest?): Response<HouseResponse>
    suspend fun updateHouse(request: HouseRequest?): Response<HouseResponse>
    suspend fun updateImg1(request: Img1Request?): Response<BasicResponse>
    suspend fun updateImg2(request: Img2Request?): Response<BasicResponse>
    suspend fun updateImg3(request: Img3Request?): Response<BasicResponse>
    suspend fun updateImg4(request: Img4Request?): Response<BasicResponse>
    suspend fun getHouseById(request: GetHouseByIdRequest?): Response<HouseResponse>
    suspend fun deleteHouseById(request: DeleteRequest?): Response<BasicResponse>

}