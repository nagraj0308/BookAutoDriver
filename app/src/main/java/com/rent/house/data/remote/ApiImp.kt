package com.rent.house.data.remote

import com.rent.house.data.remote.reqres.BasicResponse
import com.rent.house.data.remote.reqres.DeleteRequest
import com.rent.house.data.remote.reqres.GetHouseByIdRequest
import com.rent.house.data.remote.reqres.GetHouseRequest
import com.rent.house.data.remote.reqres.HouseRequest
import com.rent.house.data.remote.reqres.HouseResponse
import com.rent.house.data.remote.reqres.HousesResponse
import com.rent.house.domain.Api
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


    override suspend fun getHouseById(request: GetHouseByIdRequest?): Response<HouseResponse> {
        return service.getHouseByGmailId(request)
    }

    override suspend fun deleteHouseById(request: DeleteRequest?): Response<BasicResponse> {
        return service.deleteHouseById(request)
    }

}