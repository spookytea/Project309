package com.spookytea.project309

import android.Manifest.permission.SEND_SMS
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.spookytea.project309.view.EatView
import com.spookytea.project309.view.MainView
import com.spookytea.project309.view.PlayView
import com.spookytea.project309.view.SleepView
import com.spookytea.project309.view.StatsView
import com.spookytea.project309.view.components.AddAnAnimalDialog
import com.spookytea.project309.view.theme.Project309Theme
import com.spookytea.project309.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        println("789action $action")
        println("789data $data")



        val tabs = mutableListOf(
            StatsView(),
            SleepView(),
            EatView(),
            PlayView()
        )

        val perms = arrayOf(SEND_SMS)
        requestPermissions(perms, 8000)

        startForegroundService(Intent(this, NeedService::class.java))



        enableEdgeToEdge()

        setContent {
//            var showTrading by rememberSaveable {  mutableStateOf(false) }
//
//            showTrading = checkSelfPermission(SEND_SMS) == PackageManager.PERMISSION_GRANTED


            val isEmpty by viewModel.isEmpty.collectAsState(null)

            Project309Theme {
                when (isEmpty) {
                    true  -> AddAnAnimalDialog()
                    false -> MainView().Show(tabs.toTypedArray())
                    null  -> return@Project309Theme
                }
            }

        }
    }






}




