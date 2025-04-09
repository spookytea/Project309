package com.spookytea.project309.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

abstract class ViewBase(
    val name: String,
    val longName: String,
    val icon: ImageVector
) {
    @Composable abstract fun Show()
}