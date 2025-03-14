package com.example.project309

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AnimalView(modifier: Modifier = Modifier, viewModel: AnimalDataViewModel) {
    Text(
        text = viewModel.animal.kind,
        modifier = modifier.fillMaxSize().wrapContentHeight(),
        textAlign = TextAlign.Center
    )
}