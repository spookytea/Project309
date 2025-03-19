package com.example.project309

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SleepView(viewModel: AnimalDataViewModel){
    Column{
        AnimalView(Modifier.weight(1.0f), viewModel)
        Button(onClick = {}, Modifier.align(Alignment.CenterHorizontally).padding(5.dp)){
            Text("Sleep")
        }
    }

}