package com.example.project309

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddAnAnimalView(){
    val viewModel: AnimalDataViewModel = viewModel(LocalActivity.current as ComponentActivity)

    var name by remember {mutableStateOf("")}
    var showDialog by rememberSaveable { mutableStateOf(true) }
    val animals: Array<String> = LocalContext.current.resources.getStringArray(R.array.animals)
    val pagerState = rememberPagerState(pageCount = {animals.count()})

    var hue by remember { mutableFloatStateOf(0.5f) }

    if(showDialog) {
        Dialog({showDialog = false}) {
            Card(Modifier.size(600.dp, 600.dp)) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        pagerState,
                        modifier = Modifier.weight(0.75f),
                        beyondViewportPageCount = 1
                    ) {
                        AnimalView(Modifier.fillMaxSize(), animals[it], color = Color.hsv(hue, 1f,1f))
                    }

                    val col = Color.hsv(hue, 1f,1f)
                    Slider(
                        modifier = Modifier.weight(0.33f),
                        value = hue,
                        valueRange = 0f..360f,
                        onValueChange = { hue = it },
                        colors = SliderDefaults.colors().copy(col, col)
                    )




                    OutlinedTextField(
                        name,
                        { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    TextButton({
                        viewModel.addAnimal(name, pagerState.currentPage, hue=hue)
                        showDialog = false
                    }) {
                        Text("Add Animal")
                    }
                }

            }
        }
    }
}