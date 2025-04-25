package com.spookytea.project309.view

import android.content.ClipDescription
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.view.components.AnimalDisplay
import com.spookytea.project309.viewmodel.MainViewModel

abstract class PagerView(
    val animalSize: Float,
    val horizontalSize: Float,
    val name: String,
    val longName: String,
    val icon: ImageVector,
) {

    lateinit var pager: PagerState
    protected var landscape = false

    var dropCallBack by mutableStateOf<DragAndDropTarget?>(null)



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Show() {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val creatures by viewModel.creatures.collectAsState(listOf())
        if (creatures.isEmpty()) return



        var animalMod: Modifier = Modifier

        dropCallBack?.let {
            animalMod = Modifier.dragAndDropTarget(
                {it.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)},
                it
            )
        }

        HorizontalPager(state = pager) { page ->
            viewModel.selectedIndex = page
            when(LocalConfiguration.current.orientation){
                Configuration.ORIENTATION_LANDSCAPE -> Row {
                    AnimalDisplay(page, animalMod.weight(horizontalSize, true))
                    AdditionalContent(page, Modifier.weight(1-horizontalSize, true))
                    landscape = true

                }
                else -> Column {
                    AnimalDisplay(page, animalMod.weight(animalSize))
                    AdditionalContent(page)
                }
            }
        }
    }

    @Composable
    private fun AnimalDisplay(page: Int, modifier: Modifier = Modifier){
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val creatures by viewModel.creatures.collectAsState(listOf())
        if (creatures.isEmpty()) return
        val toDisplay = creatures[page]

        if (viewModel.isAsleep(toDisplay)) {
            OutlinedCard(modifier.fillMaxSize().padding(20.dp)) {
                Column(
                    modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.NightsStay, "Asleep Icon")
                    Text("${toDisplay.name} is asleep")
                }

            }
        } else {
            AnimalDisplay(
                modifier,
                viewModel.getArt(toDisplay),
                toDisplay.name,
                Color.hsv(toDisplay.hue, 1.0f, 1.0f)
            )
        }
    }


    @Composable
    abstract fun AdditionalContent(page: Int, modifier: Modifier = Modifier)


}