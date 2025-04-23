package com.spookytea.project309.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spookytea.project309.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainView {



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Show(tabs: Array<ViewBase>){

        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val nav = rememberNavController()
        var selected by remember { mutableIntStateOf(0) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    tabs.forEachIndexed { index, t ->
                        NavigationDrawerItem(
                            label = { Text(t.name) },
                            icon = { Icon(t.icon, t.name) },
                            selected = selected == index,
                            onClick = {
                                selected = index
                                nav.navigate(t.name)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        ) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(tabs[selected].longName) },
                    navigationIcon = {
                        IconButton(onClick = {
                            drawerState.apply {
                                scope.launch { if (isOpen) close() else open() }
                            }
                        }) { Icon(Icons.Filled.Menu, "Navigation Drawer") }
                    }
                )
            }) { p ->
                val pageCount by viewModel.creatureCount.collectAsState(0)
                val pager = rememberPagerState(initialPage = viewModel.selectedIndex) { pageCount }

                NavHost(nav, startDestination = tabs[0].name, Modifier.padding(p)) {
                    tabs.forEach { tab ->
                        tab.pager = pager
                        composable(tab.name) { tab.Show() }
                    }
                }
            }
        }

    }


}