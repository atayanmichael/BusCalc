package com.example.buscalc

data class BusChoice(val busType: BusType, val cost: Float, val income: Float, val airDirt: Float)

const val CHEAPEST = "cheapest"
const val FASTEST = "fastest"
const val CLEANEST = "cleanest"