package com.spookytea.project309.view

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

abstract class AnimalDragDropViewBase(
    private val emojis: Array<String>,
    name: String,
    longName: String,
    icon: ImageVector
) : ViewBase(name, longName, icon) {


    @SuppressLint("SwitchIntDef")
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Show() {
        when (LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> Column {
                AnimalDisplay(Modifier.weight(1.0f))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) { GetEmojis(emojis) }
            }

            ORIENTATION_LANDSCAPE -> Row {
                AnimalDisplay(Modifier.weight(0.8f))
                FlowRow(Modifier.weight(0.2f)) { GetEmojis(emojis) }
            }

            ORIENTATION_UNDEFINED -> Text("Unknown Orientation")

        }

    }

    @Composable
    fun GetEmojis(emojis: Array<String>) {
        emojis.forEach { emoji ->
            Text(
                emoji,
                modifier = Modifier.padding(10.dp),
                fontSize = 50.sp
            )
        }
    }
}




