package com.example.prettyhorizontalrecyclerview

interface VehicleItemClickCallback {
    fun onItemClick(
        xPositionForFocusedBackgroundView: Float, // Х координата для бэкграунда элемента в фокусе (центр элемента)
        xPositionForHorizontalScrollView: Float // Х координта для скрола (центр предыдущего или нажатого элемента)
    )
}