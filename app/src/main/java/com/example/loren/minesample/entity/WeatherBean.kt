package com.example.loren.minesample.entity

/**
 * Copyright © 04/01/2018 by loren
 */
data class WeatherBean(var data: Data,
                       var status: Int,
                       var desc: String) {
    data class Data(var yesterday: Yesterday,
                    var city: String,
                    var aqi: String,
                    var ganmao: String,
                    var wendu: String,
                    var forecast: List<Forecast>) {
        data class Yesterday(var date: String,
                             var high: String,
                             var fx: String,
                             var low: String,
                             var fl: String,
                             var `type`: String)

        data class Forecast(var date: String,
                            var high: String,
                            var fengli: String,
                            var low: String,
                            var fengxiang: String,
                            var `type`: String)
    }
}