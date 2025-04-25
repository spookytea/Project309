package com.spookytea.project309.view

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel

class SleepView : ViewBase(
    1.0f,
    0.9f,
    "Sleep",
    "Send your pet to sleep",
    Icons.Outlined.Bedtime
) {
    @Composable
    @SuppressLint("SwitchIntDef")
    override fun AdditionalContent(page: Int, modifier: Modifier) {
        val vm: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val creatures by vm.creatures.collectAsState(listOf())
        if(creatures.isEmpty()) return
        val current = creatures[page]

        Button(
            onClick = { vm.sleep() },
            shape = if(landscape) RoundedCornerShape(10.dp) else RoundedCornerShape(20.dp),
            modifier = if(landscape) Modifier.padding(vertical = 20.dp)
                                             .padding(end=20.dp)
                                             .fillMaxHeight()
                       else Modifier.padding(bottom=20.dp)
                                   .padding(horizontal = 50.dp)
                                   .fillMaxWidth(),
            enabled = !vm.isAsleep(current)
        ) {
            Text(name)
        }



    }
}