package com.example.buscalc

val electricBus = Electric()
val dieselBus = Diesel()
val gasBus = Gas()
val buses = listOf(electricBus, dieselBus, gasBus)

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

private fun cheapestBus(routeInfo: RouteInfo): Pair<BusType, Float> {
    var leastCost: Float? = null
    var busType: BusType? = null
    buses.forEach { bus ->
        bus.calculateCost(routeInfo.distance, routeInfo.stopsCount)
            .takeIf { leastCost == null || it < leastCost!! }?.let {
                leastCost = it
                busType = bus
            }
    }
    return Pair(busType!!, leastCost!!)
}

private fun fastestBus(routeInfo: RouteInfo): Pair<BusType, Float> {
    var fastest: Float? = null
    var busType: BusType? = null
    buses.forEach { bus ->
        bus.calculateTime(routeInfo.distance, routeInfo.stopsCount)
            .takeIf { fastest == null || it < fastest!! }?.let {
                fastest = it
                busType = bus
            }
    }
    return Pair(busType!!, fastest!!)
}

private fun cleanestBus(routeInfo: RouteInfo): Pair<BusType, Float> {
    var cleanest: Float? = null
    var busType: BusType? = null
    buses.forEach { bus ->
        bus.calculateDirt(routeInfo.distance)
            .takeIf { cleanest == null || it < cleanest!! }?.let {
                cleanest = it
                busType = bus
            }
    }
    return Pair(busType!!, cleanest!!)
}