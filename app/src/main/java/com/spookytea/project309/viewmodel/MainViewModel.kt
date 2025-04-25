package com.spookytea.project309.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.spookytea.project309.model.Creature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.min
import kotlin.random.Random

class MainViewModel(app: Application) : PagerViewModel(app) {


    fun addCreature(name:String, artIndex: Int, hue: Float) = viewModelScope.launch {
        Creature(name = name, artIndex = artIndex, hue = hue).apply { dao.add(this) }
    }

    fun upHunger(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        val current = dao.getCurrentSuspend(selectedIndex)
        //Gets random hunger value so different pets have different liked foods
        val rand = (15..35).random(Random(index + current.id))
        dao.update(current.copy(hungerLevel = min(current.hungerLevel + rand, 100)))
    }

    fun startPlay(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        while(isActive) {
            val current = dao.getCurrentSuspend(selectedIndex)
            //Seeds so different pets have different likes
            val rand = (3..5).random(Random(index + current.id))
            dao.update(current.copy(funLevel = min(current.funLevel + rand, 100)))
        }

    }

    fun sleep() = viewModelScope.launch(Dispatchers.IO){
        dao.update(
            dao.getCurrent(selectedIndex).copy(
                sleepUntil = LocalDateTime.now().plusHours(2).toEpochSecond(ZoneOffset.UTC)
            )
        )
    }







}