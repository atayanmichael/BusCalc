package com.example.buscalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusViewModel : ViewModel() {
    private val _busLiveData by lazy { MutableLiveData<Map<String, BusChoice>>() }
    val busLiveData: LiveData<Map<String, BusChoice>> by lazy { _busLiveData }

    fun getBusChoices(routeInfo: RouteInfo?) {
        _busLiveData.value = calculateBusInfo(routeInfo ?: run {
            _busLiveData.value = mapOf()
            return
        })
    }

}