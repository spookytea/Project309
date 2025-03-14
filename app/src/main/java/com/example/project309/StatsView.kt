package com.example.project309

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun StatsView(viewModel: AnimalDataViewModel) {


    val mod_bar = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .height(30.dp)
        .clip(RoundedCornerShape(10.dp))

    val barColor = { num: Float ->
        when {
            num < 0.4f -> Color.Red
            num < 0.7f -> Color.Yellow
            else -> Color.Green
        }
    }

    Column {
        AnimalView(Modifier.weight(1.0f), viewModel)
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