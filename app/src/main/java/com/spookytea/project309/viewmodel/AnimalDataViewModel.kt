package com.spookytea.project309.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spookytea.project309.R
import com.spookytea.project309.model.Creature
import com.spookytea.project309.model.DB
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CreatureViewModel(private val app: Application) : AndroidViewModel(app){

    private val dao = DB.getDB(app.applicationContext).creatureDao()
    var creatures = dao.getAll()
        private set

    var selectedIndex by mutableIntStateOf(0)

    
    val isEmpty = dao.rowCount().map { it == 0 }

    val creatureCount = dao.rowCount()
    

    fun addCreature(name:String, artIndex: Int, hue: Float) = viewModelScope.launch {
        Creature(name = name, artIndex = artIndex, hue = hue).apply {
            dao.add(this)
        }
    }




    fun getArt(creature: Creature): String =
        app.applicationContext.resources.getStringArray(R.array.animals)[creature.artIndex]











}