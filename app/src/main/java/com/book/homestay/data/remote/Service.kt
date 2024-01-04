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
    @POST("api/getAllActiveHomestay/")
    suspend fun getAllActiveHomestay(@Body request: GetHomestayRequest?): Response<HomestaysResponse>

    @Headers("Content-Type: application/json")
    @POST("api/insertHomestay/")
    suspend fun insertHomestay(@Body request: HomestayRequest?): Response<HomestayResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHomestay/")
    suspend fun updateHomestay(@Body request: HomestayRequest?): Response<HomestayResponse>


    @Headers("Content-Type: application/json")
    @POST("api/getHomestayById/")
    suspend fun getHomestayByGmailId(@Body request: GetHomestayByIdRequest?): Response<HomestayResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHomestayImg1/")
    suspend fun updateImg1(@Body request: Img1Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHomestayImg2/")
    suspend fun updateImg2(@Body request: Img2Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHomestayImg3/")
    suspend fun updateImg3(@Body request: Img3Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHomestayImg4/")
    suspend fun updateImg4(@Body request: Img4Request?): Response<BasicResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteHomestayById/")
    suspend fun deleteHomestayById(@Body request: DeleteRequest?): Response<BasicResponse>

}