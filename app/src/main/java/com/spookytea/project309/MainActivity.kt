package com.spookytea.project309

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.SEND_SMS
import android.content.Intent
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




        val tabs = mutableListOf(
            StatsView(),
            SleepView(),
            EatView(),
            PlayView()
        )

        val perms = arrayOf(SEND_SMS, POST_NOTIFICATIONS)
        requestPermissions(perms, 8000)

        startForegroundService(Intent(this, NeedService::class.java))



        enableEdgeToEdge()

        setContent {



            val isEmpty by viewModel.isEmpty.collectAsState(null)

            Project309Theme {
                when (isEmpty) {
                    true  -> AddAnAnimalDialog()
                    false -> MainView().Show(tabs)
                    null  -> return@Project309Theme
                }
            }

        }
    }






}




