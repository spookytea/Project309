package com.spookytea.project309.view

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.model.Creature
import com.spookytea.project309.viewmodel.CreatureViewModel


@SuppressLint("SwitchIntDef")
@Composable
fun StatsView(){

    when(LocalConfiguration.current.orientation){
        ORIENTATION_PORTRAIT -> Column {
            AnimalView(Modifier.weight(0.75f))
            StatsBars()
        }

        ORIENTATION_LANDSCAPE -> Row {
            AnimalView(Modifier.weight(0.25f))
            StatsBars(Modifier.weight(0.75f).align(Alignment.CenterVertically))
        }

        ORIENTATION_UNDEFINED -> {
            Text("Unknown Orientation")
        }
    }

}


@Composable
fun StatsBars(modifier: Modifier = Modifier){
    val viewModel: CreatureViewModel = viewModel(LocalActivity.current as ComponentActivity)

    val barColor = { num: Float ->
        when {
            num < 0.4f -> Color.Red
            num < 0.7f -> Color.Yellow
            else -> Color.Green
        }
    }

    Column(modifier) {
        val index = viewModel.selectedIndex
        val creatures by viewModel.creatures.collectAsState(listOf(Creature()))
        val current = creatures[index]
        mapOf(
            "Fun"    to current.funLevel,
            "Hunger" to current.hungerLevel,
            "Energy" to current.energyLevel
        ).forEach { (name, num) ->

            val asProgress = num.toFloat() / 100

            LinearProgressIndicator(
                modifier = Modifier.padding(5.dp)
                                   .height(30.dp)
                                   .fillMaxWidth()
                                   .clip(RoundedCornerShape(10.dp)),
                progress = { asProgress },
                color = barColor(asProgress)
            )
            Text(
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                text = name + "$current",
                textAlign = TextAlign.End
            )
        }

    }



}