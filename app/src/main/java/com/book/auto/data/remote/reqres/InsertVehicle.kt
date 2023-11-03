package com.book.auto.data.remote.reqres


data class VehiclesResponse(
    val `data`: List<Vehicle>,
    val msg: String,
    val isTrue: Int
)


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
    val type: String = "",
)

