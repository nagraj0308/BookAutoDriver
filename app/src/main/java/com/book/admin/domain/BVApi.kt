package com.book.admin.domain

import com.book.admin.data.remote.reqres.AutosResponse
import retrofit2.Response


interface BVApi {
    suspend fun getAllAutoAdmin(password: String): Response<AutosResponse>
}