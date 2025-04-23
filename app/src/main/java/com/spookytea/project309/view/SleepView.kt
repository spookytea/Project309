package com.spookytea.project309.view

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.view.components.AnimalDisplay
import com.spookytea.project309.viewmodel.MainViewModel

class SleepView : ViewBase(
    "Sleep",
    "Send your pet to sleep",
    Icons.Outlined.Bedtime
) {
    @SuppressLint("SwitchIntDef")
    @Composable
    override fun Show() {
        val vm: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        when (LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> Column {
                AnimalDisplay(pager, Modifier.weight(1.0f))
                Button(
                    onClick = { onClick(vm) },
                    Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 5.dp)
                        .width(200.dp)
                ) {
                    Text(name)
                }
            }

            ORIENTATION_LANDSCAPE -> Row {
                AnimalDisplay(pager, Modifier.weight(0.75f))
                Button(
                    onClick = { onClick(vm) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(0.25f)
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 0.dp,
                            end = 25.dp,
                            top = 25.dp,
                            bottom = 25.dp
                        )
                ) {
                    Text(name)
                }
            }

            ORIENTATION_UNDEFINED -> {
                Text("Unknown Orientation")
            }
        }
    }
    fun onClick(viewModel: MainViewModel) {
        viewModel.sleep()
    }
}