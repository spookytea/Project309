package com.spookytea.project309.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RamenDining
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.MainViewModel

class EatView : AnimalDragDropViewBase(
    arrayOf("⚾", "🎮"),
    "Feed",
    "Feed your pet",
    Icons.Outlined.RamenDining
) {
    @Composable
    override fun AdditionalContent(page: Int, modifier: Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)


        var clipData: String? by remember { mutableStateOf(null) }
        dropCallBack = remember {
            object : DragAndDropTarget {

                var index = 0

                override fun onEnded(event: DragAndDropEvent) {
                    viewModel.upHunger(index)
                }
                override fun onDrop(event: DragAndDropEvent): Boolean {

                    clipData = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString()
                    index = emojis.indexOf(clipData)
                    return true
                }
            }
        }
        super.AdditionalContent(page, modifier)
    }
}