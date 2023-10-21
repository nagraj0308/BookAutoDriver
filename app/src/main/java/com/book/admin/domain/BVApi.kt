package com.book.admin.domain

import com.book.admin.data.remote.reqres.AutosResponse
import com.book.admin.data.remote.reqres.BasicResponse
import retrofit2.Response
import retrofit2.http.Query


interface BVApi {
    suspend fun getAllAutoAdmin(password: String): Response<AutosResponse>

    suspend fun changeAutoStatusV1(
        password: String,
        verificationState: String,
        vId: String
    ): Response<BasicResponse>

}