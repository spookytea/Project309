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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

//Base class to hold shared drag and drop data for eating + playing
abstract class AnimalDragDropViewBase(
    protected val emojis: Array<String>,
    name_id: Int,
    longName_id: Int,
    icon: ImageVector
) : ViewBase(1.0f, 0.9f, name_id, longName_id, icon) {


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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) { GetEmojis(emojis, viewModel.isAsleep(current)) }


            ORIENTATION_LANDSCAPE -> FlowRow(Modifier.padding(end=20.dp).width(48.dp)) { //Overflowing row in case items don't fit
                GetEmojis(emojis, viewModel.isAsleep(current))
            }

        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun GetEmojis(emojis: Array<String>, isAsleep: Boolean) {


        emojis.forEach { emoji ->

            //Change display and prevent usage of emojis when asleep
            val mod = if(isAsleep) Modifier.alpha(0.5f) else Modifier.dragAndDropSource {
                detectTapGestures(onPress = { //Handles sending data via drag and drop
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




