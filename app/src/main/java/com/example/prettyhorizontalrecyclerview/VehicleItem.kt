package com.example.prettyhorizontalrecyclerview

data class VehicleItem(
    val vehicleDrawableId: Int,
    val vehicleType: String,
    val vehiclePrice: Int,
    val currency:String = "$"
)