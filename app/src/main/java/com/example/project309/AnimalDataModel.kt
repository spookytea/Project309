package com.example.project309

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.lang.Integer.parseInt

data class AnimalDataModel(
    var energyLevel: Int = 20,
    var hungerLevel: Int = 20,
    var funLevel: Int = 100,
    var kind: String = "Cat"
)

class AnimalDataViewModel(val app: Application) : AndroidViewModel(app){

    var animal by mutableStateOf(AnimalDataModel())
        private set

    init {
        val f = File(app.applicationContext.filesDir, "animal.txt")
        if (f.createNewFile()) this.Save()

        for(i in f.readLines()){
            val arr = i.split("=")
            when(arr[0]){
                "Energy" -> animal.energyLevel = parseInt(arr[1])
                "Hunger" -> animal.hungerLevel = parseInt(arr[1])
                "Fun"    -> animal.funLevel    = parseInt(arr[1])
                "Kind"   -> animal.kind        = arr[1]
            }
        }
    }

    fun Save(){
        val f = File(app.applicationContext.filesDir, "animal.txt")
        f.writeText("""
                Energy=${animal.energyLevel}
                Hunger=${animal.hungerLevel}
                Fun=${animal.funLevel}
                Kind=${animal.kind}
            """.trimIndent()
        )
    }
}





