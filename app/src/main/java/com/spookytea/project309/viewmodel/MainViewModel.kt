package com.spookytea.project309.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.spookytea.project309.model.Creature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.min

class MainViewModel(app: Application) : PagerViewModel(app) {




    fun addCreature(name:String, artIndex: Int, hue: Float) = viewModelScope.launch {
        Creature(name = name, artIndex = artIndex, hue = hue).apply { dao.add(this) }
    }

    fun upHunger() = viewModelScope.launch(Dispatchers.IO) {
        val current = dao.getCurrent(selectedIndex)
        dao.update(current.copy(hungerLevel = min(current.hungerLevel + 20, 100)))
    }




    fun sleep(current: Creature) = viewModelScope.launch(Dispatchers.IO){
        dao.update(
            current.copy(
                sleepUntil = LocalDateTime.now().plusHours(2).toEpochSecond(ZoneOffset.UTC)
            )
        )
    }







}