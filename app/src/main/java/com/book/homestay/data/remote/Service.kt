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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface Service {

    @Headers("Content-Type: application/json")
    @POST("api/getAllActiveHouse/")
    suspend fun getAllActiveHouse(@Body request: GetHomestayRequest?): Response<HomestaysResponse>

    @Headers("Content-Type: application/json")
    @POST("api/insertHouse/")
    suspend fun insertHouse(@Body request: HomestayRequest?): Response<HomestayResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouse/")
    suspend fun updateHouse(@Body request: HomestayRequest?): Response<HomestayResponse>


    @Headers("Content-Type: application/json")
    @POST("api/getHouseById/")
    suspend fun getHouseByGmailId(@Body request: GetHomestayByIdRequest?): Response<HomestayResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouseImg1/")
    suspend fun updateImg1(@Body request: Img1Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouseImg2/")
    suspend fun updateImg2(@Body request: Img2Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouseImg3/")
    suspend fun updateImg3(@Body request: Img3Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouseImg4/")
    suspend fun updateImg4(@Body request: Img4Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteHouseById/")
    suspend fun deleteHouseById(@Body request: DeleteRequest?): Response<BasicResponse>

}