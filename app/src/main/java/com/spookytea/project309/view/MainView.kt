package com.spookytea.project309.view

import android.Manifest.permission.SEND_SMS
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spookytea.project309.R
import com.spookytea.project309.view.components.ImportDialog
import com.spookytea.project309.view.components.SendDialog
import com.spookytea.project309.viewmodel.MainViewModel
import kotlinx.coroutines.launch

//Handles navigation and scaffold for application
class MainView {

    //Get started with Jetpack Compose (no date) Android Developers. Available at: https://developer.android.com/develop/ui/compose/documentation.
    //Material Design (no date) Material Design. Available at: https://m3.material.io.
    //Material Symbols and Icons (no date) Google Fonts. Available at: https://fonts.google.com/icons.
    //Docs used throughout for knowing which GUI elements and icons to use.



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Show(){

        val tabs = arrayOf(
            StatsView(),
            SleepView(),
            EatView(),
            PlayView()
        )



        val viewModel: MainViewModel = viewModel(LocalActivity.current as ComponentActivity)
        val nav = rememberNavController()
        var selected by remember { mutableIntStateOf(0) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    //Generates navigation links for all views in array
                    val context = LocalContext.current
                    tabs.forEachIndexed { index, t ->
                        NavigationDrawerItem(
                            label = { Text(stringResource(t.name_id))},
                            icon = { Icon(t.icon, stringResource(t.name_id)) },
                            selected = selected == index,
                            onClick = {
                                selected = index
                                nav.navigate(getString(context, t.name_id))
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                    //Defined later: shows buttons for sending and receiving creatures with SMS
                    SMSButtons()
                }
            }
        ) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(tabs[selected].longName_id)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            drawerState.apply {
                                scope.launch { if (isOpen) close() else open() }
                            }
                        }) { Icon(Icons.Filled.Menu, stringResource(R.string.navigation_drawer)) }
                    }
                )
            }) { p ->
                val pageCount by viewModel.creatureCount.collectAsState(0)
                val pager = rememberPagerState(initialPage = viewModel.selectedIndex) { pageCount }
                val context = LocalContext.current
                //Vasava, K. (2024) ‘Navigation in Jetpack compose. Full guide Beginner to Advanced.’, Medium, 2 April. Available at: https://medium.com/@KaushalVasava/navigation-in-jetpack-compose-full-guide-beginner-to-advanced-950c1133740.

                NavHost(nav, startDestination = stringResource(tabs[0].name_id), Modifier.padding(p)) {
                    tabs.forEach { tab ->
                        //Makes a view and passes a shared pager state for each tab so state is preserved across view switches
                        //Routing done using strings as I found this simpler and args weren't needed to be passed

                        tab.pager = pager
                        composable(getString(context, tab.name_id)) { tab.Show() }
                    }
                }
            }
        }

    }

    private enum class SMSDialog{ None, Send, Import } //enum to decide which dialog to show

    @Composable
    private fun SMSButtons() {
        var showTrading by rememberSaveable {  mutableStateOf(false) }
        showTrading = checkSelfPermission(LocalContext.current, SEND_SMS) == PackageManager.PERMISSION_GRANTED
        if(!showTrading) return //Hides buttons if no SMS permission

        var showDialog by rememberSaveable { mutableStateOf(SMSDialog.None) }
        Column {
            Spacer(Modifier.weight(0.9f)) //Pushes buttons to bottom
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
                    Icon(Icons.AutoMirrored.Filled.CallMade, stringResource(R.string.send_icon))
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
                    Icon(Icons.AutoMirrored.Filled.CallReceived, stringResource(R.string.import_icon))
                    Text(stringResource(R.string.import_string))
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