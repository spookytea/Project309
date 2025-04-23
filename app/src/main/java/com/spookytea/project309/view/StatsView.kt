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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel

class StatsView : ViewBase(
    "Stats",
    "Pet Statistics",
    Icons.Outlined.HealthAndSafety
){
    @SuppressLint("SwitchIntDef")
    @Composable
    override fun Show() {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val creatures by viewModel.creatures.collectAsState(listOf())
        if(creatures.isEmpty()) return

        // Handles Rotation

        HorizontalPager(state = pager, beyondViewportPageCount = 1) { page ->
            viewModel.selectedIndex = page
            when (LocalConfiguration.current.orientation) {
                ORIENTATION_PORTRAIT -> Column {
                    this@StatsView.AnimalDisplay(page, Modifier.weight(0.75f))
                    StatsBars(page)
                }

                ORIENTATION_LANDSCAPE -> Row {
                    this@StatsView.AnimalDisplay(
                        page,
                        Modifier.weight(0.25f)
                    )
                    StatsBars(page, Modifier.weight(0.75f).align(Alignment.CenterVertically))
                }

                ORIENTATION_UNDEFINED -> {
                    Text("Unknown Orientation")
                }
            }
        }

    }


    @Composable
    private fun StatsBars(page: Int, modifier: Modifier = Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)

        //Changes bar colour based on number bar represents


        Column(modifier) {
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
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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

    @Composable
    private fun AnimalDisplay(page: Int, modifier: Modifier = Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val creatures by viewModel.creatures.collectAsState(listOf())


        if(creatures.isNotEmpty()) {

            val toDisplay = creatures[page]

            com.spookytea.project309.view.components.AnimalDisplay(
                modifier,
                viewModel.getArt(toDisplay),
                toDisplay.name,
                Color.hsv(toDisplay.hue, 1.0f, 1.0f)
            )
        }
    }


}