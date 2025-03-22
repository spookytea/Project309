package com.example.project309

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("SwitchIntDef")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimalAndIconView(emojis: Array<String>) {
    val viewModel: AnimalDataViewModel = viewModel(LocalActivity.current as ComponentActivity)
    when(LocalConfiguration.current.orientation){
        ORIENTATION_PORTRAIT -> Column {
            AnimalView(Modifier.weight(1.0f), viewModel.animal.art, viewModel.animal.name)
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) { GetEmojis(emojis) }
        }

        ORIENTATION_LANDSCAPE -> Row {
            AnimalView(Modifier.weight(0.8f), viewModel.animal.art, viewModel.animal.name)
            FlowRow(Modifier.weight(0.2f)) { GetEmojis(emojis) }
        }

        ORIENTATION_UNDEFINED -> Text("Unknown Orientation")

    }

}

@Composable
fun GetEmojis(emojis: Array<String>){
    emojis.forEach { emoji ->
        Text(
            emoji,
            modifier = Modifier.padding(10.dp),
            fontSize = 50.sp
        )
    }
}
