package com.spookytea.project309.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SportsEsports
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
import kotlinx.coroutines.Job

class PlayView : AnimalDragDropViewBase(
    arrayOf("⚾", "🎮"),
    "Play",
    "Play with your pet",
    Icons.Outlined.SportsEsports
) {
    @Composable
    override fun AdditionalContent(page: Int, modifier: Modifier) {
        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)

        var clipData: String? by remember { mutableStateOf(null) }
        dropCallBack = remember {
            object : DragAndDropTarget {

                var index: Int = 0

                var job: Job? = null

                override fun onEntered(event: DragAndDropEvent) {
                    if(job == null) job = viewModel.startPlay(index)
                }

                override fun onExited(event: DragAndDropEvent) {
                    job!!.cancel()
                    job = null
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