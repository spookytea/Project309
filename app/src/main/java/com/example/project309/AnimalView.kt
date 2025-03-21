package com.example.project309

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun AnimalView(modifier: Modifier = Modifier, viewModel: AnimalDataViewModel) {
    var size by remember { mutableStateOf(10.em) } // Stupidly large to be scaled down later
    OutlinedCard(modifier.fillMaxWidth().padding(20.dp)) {
        Text(
            text = viewModel.animal.art,
            modifier = modifier.fillMaxSize().wrapContentSize(),
            style = TextStyle(fontSize = size),
            fontFamily = FontFamily.Monospace,
            maxLines = viewModel.animal.art.count{it == '\n'}, //Makes sure lines are not split
            onTextLayout = {tL -> if(tL.didOverflowHeight || tL.didOverflowWidth) size *= 0.9F}
            //Scales down font size to  fit space

        )
        Text(viewModel.animal.name, Modifier.fillMaxWidth().padding(bottom = 10.dp), textAlign = TextAlign.Center)
    }



}