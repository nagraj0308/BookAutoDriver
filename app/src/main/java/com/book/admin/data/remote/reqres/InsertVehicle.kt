package com.book.admin.data.remote.reqres

import com.google.gson.annotations.SerializedName


data class VehicleRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("deactivated") val deactivated: Boolean?,
    @SerializedName("driver") val driver: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("mobileNo") val mobileNo: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("rate") val rate: String?,
    @SerializedName("type") val type: String?
)

data class VehicleLocationRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)

data class GetVehicleByGmailIdRequest(
    @SerializedName("gmailId") val gmailId: String?,
)

data class DeleteVehicleRequest(
    @SerializedName("vId") val vId: String?,
)

data class AutosResponse(
    val `data`: List<Auto>,
    val msg: String,
    val isTrue: Int
)

data class BasicResponse(
    val msg: String,
    val isTrue: Int
)


data class Auto(
    val _id: String = "",
    val name: String = "",
    val deactivated: Boolean = false,
    val driver: String = "",
    val imageUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: Long = 0,
    val number: String = "",
    val rate: String = "",
    val type: String = "",
    val verificationState: String = "U"
)
