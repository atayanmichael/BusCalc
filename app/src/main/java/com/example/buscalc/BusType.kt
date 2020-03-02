package com.example.buscalc

import kotlin.math.floor

private const val DISTANCE_ELECTRIC = 600f
private const val DISTANCE_DIESEL = 1000f
private const val DISTANCE_GAS = 800f

private const val AIR_DIRT_ELECTRIC = 0f
private const val AIR_DIRT_DIESEL = 380f
private const val AIR_DIRT_GAS = 290f

private const val USAGE_ELECTRIC = 60f
private const val USAGE_DIESEL = 16f
private const val USAGE_GAS = 18f

private const val CHARGE_TIME_ELECTRIC = 40f
private const val CHARGE_TIME_DIESEL = 10f
private const val CHARGE_TIME_GAS = 25f

private const val STOP_PERCENT = 0.008f

sealed class BusType(
    private val distance: Float,
    private val airDirt: Float,
    private val usage: Float,
    private val chargingTime: Float
) {
    var cost: Float = 0f
    private fun distanceStopsIncluded(stopsCount: Int) = distance * (1f - stopsCount * STOP_PERCENT)
    private fun chargeCount(routeDistance: Float, stopsCount: Int) =
        floor(routeDistance / distanceStopsIncluded(stopsCount))

    fun calculateTime(routeDistance: Float, stopsCount: Int) =
        chargeCount(routeDistance, stopsCount) * chargingTime

    fun calculateDirt(routeDistance: Float) = routeDistance * airDirt

    fun calculateCost(routeDistance: Float, stopsCount: Int): Float =
        chargeCount(routeDistance, stopsCount).let {
            val distanceLeft = routeDistance - it * distanceStopsIncluded(stopsCount)
            usage * cost * (it + distanceLeft / distance)
        }
}

class Electric : BusType(DISTANCE_ELECTRIC, AIR_DIRT_ELECTRIC, USAGE_ELECTRIC, CHARGE_TIME_ELECTRIC)
class Diesel : BusType(DISTANCE_DIESEL, AIR_DIRT_DIESEL, USAGE_DIESEL, CHARGE_TIME_DIESEL)
class Gas : BusType(DISTANCE_GAS, AIR_DIRT_GAS, USAGE_GAS, CHARGE_TIME_GAS)