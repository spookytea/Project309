package com.example.project309

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class AnimalDataViewModel(private val app: Application) : AndroidViewModel(app){

    var animal by mutableStateOf(AnimalDataModel())
        private set


    fun Load(){
        val f = File(app.applicationContext.filesDir, "animal.json")
        if(!f.exists()) return
        animal = Json.decodeFromString(f.readText())
    }

    fun addAnimal(name:String, artIndex: Int, hue: Float) {
        animal = AnimalDataModel(name = name, artIndex = artIndex, hue = hue)
        val f = File(app.applicationContext.filesDir, "animal.json")
        f.writeText(Json.encodeToString(animal))
    }


    fun getArt(): String {
        return app.applicationContext.resources.getStringArray(R.array.animals)[animal.artIndex]
    }

}