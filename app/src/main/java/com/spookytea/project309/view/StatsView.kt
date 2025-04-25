package com.spookytea.project309.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel

class StatsView : PagerView(
    0.75f,
    0.35f,
    "Stats",
    "Pet Statistics",
    Icons.Outlined.HealthAndSafety
){
    @Composable
    override fun AdditionalContent(page: Int, modifier: Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)

        var mod = modifier

        if(landscape) mod = mod.fillMaxHeight()


        //Changes bar colour based on number bar represents
        Column(modifier = mod, verticalArrangement = Arrangement.Center) {
            val creatures by viewModel.creatures.collectAsState(listOf())
            if(creatures.isEmpty()) return

            val current = creatures[page]
            mapOf(
                "Fun" to current.funLevel,
                "Hunger" to current.hungerLevel,
                "Energy" to current.energyLevel
            ).forEach { (name, num) ->

                //Score stored out of 100 instead of as decimal
                val asProgress = num.toFloat() / 100
                println("$name: $num")

                LinearProgressIndicator(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(10.dp)).fillMaxWidth(),
                    progress = { asProgress },
                    color = when {
                        asProgress < 0.4f -> Color.Red
                        asProgress < 0.7f -> Color.Yellow
                        else -> Color.Green
                    },
                    strokeCap = StrokeCap.Butt,
                    gapSize = 0.dp,
                    drawStopIndicator = {},

                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                    text = name,
                    textAlign = TextAlign.End
                )
            }

        }



    }



}