package com.example.project309

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AnimalView(modifier: Modifier = Modifier) {
    Text(
        text = "Animal Goes Here",
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(),
        textAlign = TextAlign.Center
    )
}