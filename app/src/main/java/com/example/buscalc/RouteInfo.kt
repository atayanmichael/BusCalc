package com.example.buscalc

data class RouteInfo(
    val distance: Float,
    val stopsCount: Int,
    val peopleCount: Int,
    val ticketPrice: Float,
    val electricityPrice: Float,
    val dieselPrice: Float,
    val gasPrice: Float
)