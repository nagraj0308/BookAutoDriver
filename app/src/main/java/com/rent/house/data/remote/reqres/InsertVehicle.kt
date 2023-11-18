package com.rent.house.data.remote.reqres

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class VehicleRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("deactivated") val deactivated: Boolean?,
    @SerializedName("driver") val driver: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("mobileNo") val mobileNo: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("rate") val rate: String?,
    @SerializedName("typeId") val typeId: Int?
)

data class GetVehicleRequest(
    @SerializedName("lat") val lat: Double?, @SerializedName("lon") val lon: Double?
)

data class VehicleLocationRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
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

data class VehiclesResponse(
    val `data`: List<Vehicle>, val msg: String, val isTrue: Int
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
    val typeId: Int = 0,
    val rate: String = "",
    val verificationState: String = "U",
    val adminRemark: String = ""
) : Serializable

