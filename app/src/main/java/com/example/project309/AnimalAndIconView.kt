package com.example.project309

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AnimalAndIconView(viewModel: AnimalDataViewModel, name: String, icons: Array<ImageVector>) {
    Column {
        AnimalView(Modifier.weight(1.0f), viewModel)
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            icons.forEach { icon ->
                Image(
                    icon,
                    "$name item",
                    Modifier.size(72.dp).padding(10.dp)
                )
            }
        }
    }
}