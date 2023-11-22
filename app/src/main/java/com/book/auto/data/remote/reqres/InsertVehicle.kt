package com.book.auto.data.remote.reqres

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountRequest(
    @SerializedName("vId") val vId: String?,
)

data class BasicResponse(
    val msg: String,
    val isTrue: Int
)

data class VehiclesResponse(
    val `data`: List<Auto>,
    val msg: String,
    val isTrue: Int
)


data class Auto(
    val _id: String = "",
    val driver: String = "",
    val imageUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: Long = 0,
    val number: String = "",
    val type: String = "",
) : Serializable

