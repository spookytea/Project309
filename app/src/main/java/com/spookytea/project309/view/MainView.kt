package com.spookytea.project309.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spookytea.project309.view.components.ImportDialog
import com.spookytea.project309.view.components.SendDialog
import com.spookytea.project309.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainView {



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Show(tabs: List<PagerView>){

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

                    SMSButtons()
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

    private enum class SMSDialog{ None, Send, Import }

    @Composable
    private fun SMSButtons() {



        var showDialog by rememberSaveable { mutableStateOf(SMSDialog.None) }
        Column {
            Spacer(Modifier.weight(0.9f))
            Row(Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp, end = 50.dp, start = 50.dp)) {
                Button(
                    onClick = { showDialog = SMSDialog.Send },
                    modifier = Modifier.weight(0.5f),
                    shape = RoundedCornerShape(
                        bottomEnd = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 20.dp,
                        topStart = 20.dp
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.CallMade, "Send Icon")
                    Text("Send")
                }
                OutlinedButton(
                    onClick = {showDialog = SMSDialog.Import},
                    modifier = Modifier.weight(0.5f),
                    shape = RoundedCornerShape(
                        bottomStart = 0.dp,
                        topStart = 0.dp,
                        bottomEnd = 20.dp,
                        topEnd = 20.dp
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.CallReceived, "Import Icon")
                    Text("Import")
                }
            }

        }

        when(showDialog){
            SMSDialog.Send   -> SendDialog{showDialog = SMSDialog.None}
            SMSDialog.Import -> ImportDialog{showDialog = SMSDialog.None}
            SMSDialog.None   -> {}

        }

    }


}