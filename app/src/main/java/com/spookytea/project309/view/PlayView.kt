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
        dropCallBack = remember { //callback for when dropped
            // Drag and drop | Jetpack Compose (no date) Android Developers. Available at: https://developer.android.com/develop/ui/compose/touch-input/user-interactions/drag-and-drop.
            object : DragAndDropTarget {

                var index: Int = 0

                var job: Job? = null

                override fun onEntered(event: DragAndDropEvent) {
                    //Stores co-routine job for cancelling later.
                    // Checks if null first to avoid multiple jobs starting if multiple events fired
                    // Ups fun continuously until no longer hovering
                    if(job == null) job = viewModel.startPlay(index)
                }

                override fun onExited(event: DragAndDropEvent) {
                    //cancels and resets when exited to stop upping fun
                    job!!.cancel()
                    job = null
                }

                override fun onDrop(event: DragAndDropEvent): Boolean {
                    //Gets emoji dropped
                    clipData = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString()
                    index = emojis.indexOf(clipData)

                    return true
                }
            }
        }
        super.AdditionalContent(page, modifier)
    }
}