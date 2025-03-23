package com.example.project309

import kotlinx.serialization.Serializable

@Serializable
data class AnimalDataModel(
    val energyLevel: Int = 100,
    val hungerLevel: Int = 100,
    val funLevel: Int = 100,
    val name: String = "",
    val artIndex: Int = 0,
    val hue: Float = 0f
)





