package com.example.project309

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimalAndIconView(viewModel: AnimalDataViewModel, emojis: Array<String>) {
    Column {
        AnimalView(Modifier.weight(1.0f), viewModel)
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            emojis.forEach { emoji ->
                Text(
                    emoji,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 50.sp
                )
            }
        }
    }
}


