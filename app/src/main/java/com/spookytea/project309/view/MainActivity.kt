package com.spookytea.project309.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spookytea.project309.view.theme.Project309Theme
import com.spookytea.project309.viewmodel.CreatureViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val tabs = arrayOf(StatsView(), SleepView(), EatView(), PlayView())

    private val viewModel by viewModels<CreatureViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        CoroutineScope(Dispatchers.IO).launch {   DB.getDB(applicationContext).clearAllTables() }


        setContent {

            val isEmpty by viewModel.isEmpty.collectAsState(null)


            Project309Theme {
                when (isEmpty) {
                    true  -> AddAnAnimalDialog()
                    false -> MainView()
                    null  ->  return@Project309Theme
                }
            }

        }
    }

    @Composable
    private fun TradeButtons(){


        Row(Modifier.padding(20.dp)) {
            Button(
                modifier = Modifier.weight(0.5f),
                onClick = {},
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.CallMade, "Send")
                Text("Send")
            }
            OutlinedButton(
                {},
                Modifier.weight(0.5f),
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.CallReceived, "Receive")
                Text("Receive")
            }
        }


    }



    @Composable
    private fun MainView(){
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
                    TradeButtons()
                }
            }
        ) {
            Scaffold(topBar = { TopBar(selected, drawerState) }) { p ->
                NavHost(
                    nav,
                    startDestination = tabs[0].name,
                    Modifier.padding(p),
                ) {
                    tabs.forEach {
                        tab -> composable(tab.name) { tab.Show() }
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(selected: Int, drawerState: DrawerState){
        val scope = rememberCoroutineScope()
        CenterAlignedTopAppBar(
            title = { Text(tabs[selected].longName) },
            navigationIcon = {
                IconButton(onClick = {
                    drawerState.apply {
                        scope.launch{ if (isOpen) close() else open() }
                    }
                }) { Icon(Icons.Filled.Menu, "Navigation Drawer") }
            }
        )

    }
}




