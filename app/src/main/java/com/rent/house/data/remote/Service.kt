package com.rent.house.data.remote

import com.rent.house.data.remote.reqres.BasicResponse
import com.rent.house.data.remote.reqres.DeleteRequest
import com.rent.house.data.remote.reqres.GetHouseByIdRequest
import com.rent.house.data.remote.reqres.GetHouseRequest
import com.rent.house.data.remote.reqres.HouseRequest
import com.rent.house.data.remote.reqres.HouseResponse
import com.rent.house.data.remote.reqres.HousesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface Service {

    @Headers("Content-Type: application/json")
    @POST("api/getAllActiveHouse/")
    suspend fun getAllActiveHouse(@Body request: GetHouseRequest?): Response<HousesResponse>

    @Headers("Content-Type: application/json")
    @POST("api/insertHouse/")
    suspend fun insertHouse(@Body request: HouseRequest?): Response<HouseResponse>

    @Headers("Content-Type: application/json")
    @POST("api/updateHouse/")
    suspend fun updateHouse(@Body request: HouseRequest?): Response<HouseResponse>


    @Headers("Content-Type: application/json")
    @POST("api/getHouseById/")
    suspend fun getHouseByGmailId(@Body request: GetHouseByIdRequest?): Response<HouseResponse>

    @Headers("Content-Type: application/json")
    @POST("api/deleteHouseById/")
    suspend fun deleteHouseById(@Body request: DeleteRequest?): Response<BasicResponse>

}