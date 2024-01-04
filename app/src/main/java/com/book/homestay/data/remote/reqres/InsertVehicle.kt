package com.book.homestay.data.remote.reqres

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class HomestayRequest(
    @SerializedName("vId") val vId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("mobileNo") val mobileNo: String?,
    @SerializedName("rate") val rate: String?,
    @SerializedName("imageUrl1") val imageUrl1: String?,
    @SerializedName("imageUrl2") val imageUrl2: String?,
    @SerializedName("imageUrl3") val imageUrl3: String?,
    @SerializedName("imageUrl4") val imageUrl4: String?
)

data class GetHomestayRequest(
    @SerializedName("lat") val lat: Double?, @SerializedName("lon") val lon: Double?
)


data class GetHomestayByIdRequest(
    @SerializedName("vId") val gmailId: String?,
)

data class Img1Request(
    @SerializedName("vId") val id: String?,
    @SerializedName("imageUrl1") val imageUrl1: String?,
)

data class Img2Request(
    @SerializedName("vId") val id: String?,
    @SerializedName("imageUrl2") val imageUrl2: String?,
)

data class Img3Request(
    @SerializedName("vId") val id: String?,
    @SerializedName("imageUrl3") val imageUrl3: String?,
)

data class Img4Request(
    @SerializedName("vId") val id: String?,
    @SerializedName("imageUrl4") val imageUrl4: String?,
)

data class DeleteRequest(
    @SerializedName("vId") val vId: String?,
)

data class HomestayResponse(
    val `data`: Homestay, val msg: String, val isTrue: Int
)

data class HomestaysResponse(
    val `data`: List<Homestay>, val msg: String, val isTrue: Int
)

data class BasicResponse(
    val msg: String, val isTrue: Int
)


data class Homestay(
    val _id: String = "",
    val deactivated: Boolean = false,
    val name: String = "",
    val address: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: String = "0",
    val rate: String = "",
    val verificationState: String = "U",
    val adminRemark: String = "",
    val imageUrl1: String = "",
    val imageUrl2: String = "",
    val imageUrl3: String = "",
    val imageUrl4: String = ""
) : Serializable


