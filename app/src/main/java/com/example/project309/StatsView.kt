package com.example.project309

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsView() {
//    AnimalDataModel.Load()
    val progress by remember { mutableFloatStateOf(0.9f) }


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
        CenterAlignedTopAppBar(title = { Text("Creature Statistics") })
        AnimalView(Modifier.weight(1.0f))
        (1..3).forEach { _ ->
            LinearProgressIndicator(
                modifier = mod_bar,
                progress = { progress },
                color = barColor(progress)
            )
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                text = "Hunger",
                textAlign = TextAlign.End
            )
        }


    }


}