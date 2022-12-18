package com.example.flightapp2022.models

import com.example.flightapp2022.models.PointPosModel

class TrackingModel(val icao24: String,
                    val startTime: Long,
                    val endTime: Long,
                    val callsign: String,
                    val pos: List<PointPosModel>) {}