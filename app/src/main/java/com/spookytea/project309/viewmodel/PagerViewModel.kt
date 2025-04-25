package com.spookytea.project309.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.spookytea.project309.R
import com.spookytea.project309.model.Creature
import com.spookytea.project309.model.DB
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset

//Base viewModel to store required pager logic
abstract class PagerViewModel(protected val app: Application) : AndroidViewModel(app){
    protected val dao = DB.getDB(app.applicationContext).creatureDao()
    var creatures = dao.getAll()
        protected set

    var selectedIndex by  mutableIntStateOf(0)
    val creatureCount = dao.rowCount()

    fun getArt(creature: Creature): String =
        app.applicationContext.resources.getStringArray(R.array.animals)[creature.artIndex]


    val isEmpty = dao.rowCount().map { it == 0 }

    open fun isAsleep(creature: Creature) = LocalDateTime.now().isBefore(
        LocalDateTime.ofEpochSecond(creature.sleepUntil, 0, ZoneOffset.UTC)
    )



}