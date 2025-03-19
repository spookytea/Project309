package com.example.project309

import kotlinx.serialization.Serializable

@Serializable
data class AnimalDataModel(
    var energyLevel: Int = 100,
    var hungerLevel: Int = 100,
    var funLevel: Int = 100,
    var name: String = "Cat",
    var art: String = ""
)





