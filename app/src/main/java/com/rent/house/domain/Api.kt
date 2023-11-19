package com.rent.house.domain

import com.rent.house.data.remote.reqres.BasicResponse
import com.rent.house.data.remote.reqres.DeleteRequest
import com.rent.house.data.remote.reqres.GetHouseByIdRequest
import com.rent.house.data.remote.reqres.GetHouseRequest
import com.rent.house.data.remote.reqres.HouseRequest
import com.rent.house.data.remote.reqres.HouseResponse
import com.rent.house.data.remote.reqres.HousesResponse
import retrofit2.Response

interface Api {

    suspend fun getAllActiveHouse(request: GetHouseRequest?): Response<HousesResponse>
    suspend fun insertHouse(request: HouseRequest?): Response<HouseResponse>
    suspend fun updateHouse(request: HouseRequest?): Response<HouseResponse>
    suspend fun getHouseById(request: GetHouseByIdRequest?): Response<HouseResponse>
    suspend fun deleteHouseById(request: DeleteRequest?): Response<BasicResponse>

}