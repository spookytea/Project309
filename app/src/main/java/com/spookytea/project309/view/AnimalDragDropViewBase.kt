package com.spookytea.project309.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.view.components.AnimalDisplay
import com.spookytea.project309.viewmodel.MainViewModel

abstract class AnimalDragDropViewBase(
    private val emojis: Array<String>,
    name: String,
    longName: String,
    icon: ImageVector
) : ViewBase(name, longName, icon) {


    @SuppressLint("SwitchIntDef")
    @OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Show() {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val dropCallback = remember {
            object : DragAndDropTarget {
                override fun onDrop(event: DragAndDropEvent): Boolean {
                    viewModel.upHunger()
                    return true
                }
            }
        }


        when (LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> Column {
                AnimalDisplay(
                    pager,
                    Modifier
                        .weight(1.0f)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { ev ->
                                ev.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)

                            }, target = dropCallback

                        )
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) { GetEmojis(emojis) }
            }

            ORIENTATION_LANDSCAPE -> Row {
                AnimalDisplay(pager, Modifier.weight(0.8f))
                FlowRow(Modifier.weight(0.2f)) { GetEmojis(emojis) }
            }

            ORIENTATION_UNDEFINED -> Text("Unknown Orientation")

        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun GetEmojis(emojis: Array<String>) {
        emojis.forEach { emoji ->
            Text(
                emoji,
                modifier = Modifier
                    .padding(10.dp)
                    .dragAndDropSource {
                        detectTapGestures(onPress = {
                            startTransfer(
                                DragAndDropTransferData(
                                    ClipData.newPlainText("emoji", emoji)
                                )
                            )
                        })
                    },
                fontSize = 50.sp
            )
        }
    }
}




