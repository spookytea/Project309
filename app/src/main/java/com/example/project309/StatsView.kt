package com.example.project309

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@SuppressLint("SwitchIntDef")
@Composable
fun StatsView(viewModel: AnimalDataViewModel) {

    when(LocalConfiguration.current.orientation){
        ORIENTATION_PORTRAIT -> Column {
            AnimalView(Modifier.weight(1.0f), viewModel)
            StatsBars(viewModel=viewModel)
        }

        ORIENTATION_LANDSCAPE -> Row {
            AnimalView(Modifier.weight(0.25f), viewModel)
            StatsBars(Modifier.weight(0.75f).align(Alignment.CenterVertically), viewModel=viewModel)
        }

        ORIENTATION_UNDEFINED -> {
            Text("Unknown Orientation")
        }
    }

}


@Composable
fun StatsBars(modifier: Modifier = Modifier, viewModel: AnimalDataViewModel){
    val mod_bar = Modifier
        .padding(5.dp)
        .height(30.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))

    val barColor = { num: Float ->
        when {
            num < 0.4f -> Color.Red
            num < 0.7f -> Color.Yellow
            else -> Color.Green
        }
    }

    Column(modifier) {
        mapOf(
            "Fun"    to viewModel.animal.funLevel,
            "Hunger" to viewModel.animal.hungerLevel,
            "Energy" to viewModel.animal.energyLevel
        ).forEach { (name, num) ->

            val asProgress = num.toFloat() / 100

            LinearProgressIndicator(
                modifier = mod_bar,
                progress = { asProgress },
                color = barColor(asProgress)
            )
            Text(
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                text = name,
                textAlign = TextAlign.End
            )
        }

    }



}