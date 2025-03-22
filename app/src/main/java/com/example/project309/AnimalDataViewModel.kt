package com.example.project309

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.json.Json
import java.io.File

class AnimalDataViewModel(app: Application) : AndroidViewModel(app){

    var animal by mutableStateOf(AnimalDataModel())
        private set

    init {
        val f = File(app.applicationContext.filesDir, "animal.json")
        if(f.exists()) animal = Json.decodeFromString(f.readText())
    }

//    fun Save() {
//        val f = File(app.applicationContext.filesDir, "animal.json")
//        f.writeText(Json.encodeToString(animal))
//    }

    fun addAnimal(name:String, art: String){
        animal = animal.copy(name=name, art=art)
    }
}