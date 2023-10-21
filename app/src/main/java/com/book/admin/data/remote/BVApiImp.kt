package com.book.admin.data.remote

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import com.book.admin.domain.BVApi
import retrofit2.Response
import javax.inject.Inject


class BVApiImp @Inject constructor(
    private val service: BVService,
) : BVApi {

    override suspend fun getAllAutoAdmin(password: String): Response<AutosResponse> {
        return service.getAllAutoAdmin(password, "U")
    }

    override suspend fun changeAutoStatusV1(
        password: String, verificationState: String, vId: String
    ): Response<BasicResponse> {
        return service.changeAutoStatusV1(password, verificationState, vId)
    }

}