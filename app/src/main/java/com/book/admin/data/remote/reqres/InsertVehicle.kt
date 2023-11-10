package com.book.admin.data.remote.reqres

import java.io.Serializable


data class AutosResponse(
    val `data`: List<Auto>,
    val msg: String,
    val isTrue: Int
)

data class VehiclesResponse(
    val `data`: List<Vehicle>,
    val msg: String,
    val isTrue: Int
)

data class BasicResponse(
    val msg: String,
    val isTrue: Int
)


data class Auto(
    val _id: String = "",
    val deactivated: Boolean = false,
    val driver: String = "",
    val imageUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: Long = 0,
    val number: String = "",
    val type: String = "",
    val verificationState: String = "U"
) : Serializable

data class Vehicle(
    val _id: String = "",
    val deactivated: Boolean = false,
    val driver: String = "",
    val imageUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val mobileNo: String = "",
    val modifyTime: Long = 0,
    val number: String = "",
    val typeId: Int = 0,
    val verificationState: String = "U"
) : Serializable


data class VS(
    val name: String,
    val code: String
) {
    override fun toString(): String {
        return name
    }
}

