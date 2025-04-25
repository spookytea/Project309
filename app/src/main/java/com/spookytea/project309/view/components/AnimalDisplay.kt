package com.spookytea.project309.view.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel
import com.spookytea.project309.viewmodel.PagerViewModel



//Actual code to display the animal
@Composable
fun AnimalDisplay(
    modifier: Modifier = Modifier,
    art: String,
    name: String = "",
    color: Color = Color.Black
) {
    var size by remember { mutableStateOf(10.em) } // Stupidly large to be scaled down later
    var invisible by remember { mutableStateOf(true) } //Hides text until scaled correctly
    val alpha by animateFloatAsState(if(invisible) 0f else 1.0f, tween(500))//Animated fade

    OutlinedCard(modifier.fillMaxWidth().padding(20.dp)) {
        Column {

            Text(
                text = art,
                modifier = modifier.fillMaxSize().wrapContentSize().alpha(alpha),
                style = TextStyle(fontSize = size),
                fontFamily = FontFamily.Monospace,
                maxLines = art.count{it == '\n'} + 1, //Makes sure lines are not split
                onTextLayout = { tL ->
                    invisible = tL.didOverflowHeight || tL.didOverflowWidth
                    if(invisible) size *= 0.9F //Sizes down and hides until correct
                },
                color = color,
                fontWeight = FontWeight.ExtraBold
                //Scales down font size to  fit space

            )
            if(name.isNotBlank())
                Text(name, Modifier.fillMaxWidth().padding(bottom = 10.dp), textAlign = TextAlign.Center)

        }
    }
}

@Composable
fun AnimalDisplay( //Used by send dialog to show from viewmodel without sleeping
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    viewModel: PagerViewModel = viewModel<MainViewModel>(LocalActivity.current as ComponentActivity)
){
    val creatures by viewModel.creatures.collectAsState(listOf())



    HorizontalPager(pagerState, modifier.fillMaxWidth()) { page ->
        viewModel.selectedIndex = page



        if(creatures.isNotEmpty()) {
            val toDisplay = creatures[page]

            if(viewModel.isAsleep(toDisplay)){
                OutlinedCard(modifier.fillMaxSize().padding(20.dp)){
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
    }

}








