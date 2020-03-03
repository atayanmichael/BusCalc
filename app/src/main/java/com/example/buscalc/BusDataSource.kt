package com.example.buscalc

private val electricBus = Electric()
private val dieselBus = Diesel()
private val gasBus = Gas()
private val buses = listOf(electricBus, dieselBus, gasBus)

fun calculateBusInfo(routeInfo: RouteInfo): Map<String, BusChoice> {
    val choices = mutableMapOf<String, BusChoice>()
    electricBus.cost = routeInfo.electricityPrice
    dieselBus.cost = routeInfo.dieselPrice
    gasBus.cost = routeInfo.gasPrice

    val dirtyIncome = routeInfo.peopleCount * routeInfo.stopsCount * routeInfo.ticketPrice

    cheapestBus(routeInfo).let {
        choices.put(
            CHEAPEST,
            BusChoice(
                it.first, it.second, dirtyIncome - it.second,
                it.first.calculateDirt(routeInfo.distance)
            )
        )
    }

    fastestBus(routeInfo).let {
        val cost = it.first.calculateCost(routeInfo.distance, routeInfo.stopsCount)
        choices.put(
            FASTEST,
            BusChoice(
                it.first, cost, dirtyIncome - cost,
                it.first.calculateDirt(routeInfo.distance)
            )
        )
    }

    cleanestBus(routeInfo).let {
        val cost = it.first.calculateCost(routeInfo.distance, routeInfo.stopsCount)
        choices.put(
            CLEANEST,
            BusChoice(
                it.first, cost, dirtyIncome - cost,
                it.second
            )
        )
    }

    return choices
}

private fun cheapestBus(routeInfo: RouteInfo) = findBusWithMetric(routeInfo, CHEAPEST)
private fun fastestBus(routeInfo: RouteInfo) = findBusWithMetric(routeInfo, FASTEST)
private fun cleanestBus(routeInfo: RouteInfo) = findBusWithMetric(routeInfo, CLEANEST)

private fun findBusWithMetric(routeInfo: RouteInfo, metric: String): Pair<BusType, Float> {
    var minimum: Float? = null
    var busType: BusType? = null
    buses.forEach { bus ->
        when (metric) {
            CHEAPEST -> bus.calculateCost(routeInfo.distance, routeInfo.stopsCount)
            CLEANEST -> bus.calculateDirt(routeInfo.distance)
            FASTEST -> bus.calculateTime(routeInfo.distance, routeInfo.stopsCount)
            else -> -1f
        }.takeIf { minimum == null || it < minimum!! }?.let {
            minimum = it
            busType = bus
        }
    }
    return Pair(busType!!, minimum!!)
}