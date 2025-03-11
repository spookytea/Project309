package com.example.project309

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
fun StatsView(){
    AnimalDataModel.Load()
    LazyColumn {
        for(i in listOf(AnimalDataModel.funLevel, AnimalDataModel.hungerLevel, AnimalDataModel.energyLevel, AnimalDataModel.kind))
            item {
                Text(i.toString())
            }
    }

}