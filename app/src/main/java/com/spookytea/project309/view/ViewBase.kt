package com.spookytea.project309.view

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

abstract class ViewBase(
    val name: String,
    val longName: String,
    val icon: ImageVector
) {
    lateinit var pager: PagerState
    @Composable abstract fun Show()

}