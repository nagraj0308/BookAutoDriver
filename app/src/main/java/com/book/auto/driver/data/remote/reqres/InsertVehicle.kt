package com.book.auto.driver.data.remote.reqres

import com.google.gson.annotations.SerializedName


data class VehicleRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("deactivated") val deactivated: Boolean?,
    @SerializedName("driver") val driver: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("mobileNo") val mobileNo: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("type") val type: String?
)

data class VehicleLocationRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)


data class VerificationStatusRequest(
    @SerializedName("vId") val vId: String?, @SerializedName("deactivated") val boolean: Boolean?
)

data class GetVehicleByGmailIdRequest(
    @SerializedName("vId") val gmailId: String?,
)

data class DeleteVehicleRequest(
    @SerializedName("vId") val vId: String?,
)

data class VehicleResponse(
    val `data`: Vehicle, val msg: String, val isTrue: Int
)

data class BasicResponse(
    val msg: String, val isTrue: Int
)


data class Vehicle(
    val _id: String = "",
    val deactivated: Boolean = false,
    val driver: String = "",
    val imageUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: String = "0",
    val number: String = "",
    val type: String = "",
    val verificationState: String = "U",
    val adminRemark: String = "",
    val selectCount: Long = 0,
    val callCount: Long = 0
)

