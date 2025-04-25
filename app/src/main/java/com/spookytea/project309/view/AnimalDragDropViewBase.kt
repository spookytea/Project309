package com.spookytea.project309.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel

abstract class AnimalDragDropViewBase(
    protected val emojis: Array<String>,
    name: String,
    longName: String,
    icon: ImageVector
) : PagerView(1.0f, 0.8f, name, longName, icon) {


    @SuppressLint("SwitchIntDef")
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun AdditionalContent(page: Int, modifier: Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)

        val creatures by viewModel.creatures.collectAsState(listOf())
        if(creatures.isEmpty()) return

        val current = creatures[page]



        when (LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp)
            ) { GetEmojis(emojis, viewModel.isAsleep(current)) }


            ORIENTATION_LANDSCAPE -> FlowRow(Modifier.padding(end=20.dp)) {
                GetEmojis(emojis, viewModel.isAsleep(current))
            }

        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun GetEmojis(emojis: Array<String>, isAsleep: Boolean) {


        emojis.forEach { emoji ->

            val mod = if(isAsleep) Modifier.alpha(0.5f) else Modifier.dragAndDropSource {
                detectTapGestures(onPress = {
                    startTransfer(
                        DragAndDropTransferData(
                            clipData = ClipData.newPlainText("emoji", emoji)
                        )
                    )
                })
            }


            Text(
                emoji,
                modifier = mod.padding(10.dp),
                fontSize = 50.sp
            )
        }
    }
}




