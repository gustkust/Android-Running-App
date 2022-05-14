package com.example.sport_road_app

import java.io.Serializable

data class Route(
    val id: Int,
    val name: String,
    val description: String,
    val length: Double,
    val location: String,
    val difficulty: String,
    val time: String
) : Serializable