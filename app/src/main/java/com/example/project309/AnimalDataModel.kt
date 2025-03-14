package com.example.project309

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class AnimalDataModel(
    var energyLevel: Int = 100,
    var hungerLevel: Int = 100,
    var funLevel: Int = 100,
    var name: String = "Cat",
    var art: String = ""
)

class AnimalDataViewModel(private val app: Application) : AndroidViewModel(app){

    var animal by mutableStateOf(AnimalDataModel())
        private set

    init {
        val f = File(app.applicationContext.filesDir, "animal.json")
        if (f.createNewFile()) this.Save()
        animal = Json.decodeFromString(f.readText())
    }

    fun Save() {
        val f = File(app.applicationContext.filesDir, "animal.json")
        f.writeText(Json.encodeToString(animal))
    }
}





