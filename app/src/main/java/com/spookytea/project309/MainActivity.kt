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
import com.spookytea.project309.view.MainView
import com.spookytea.project309.view.components.AddAnAnimalDialog
import com.spookytea.project309.view.theme.Project309Theme
import com.spookytea.project309.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Request runtime permissions | Privacy (no date) Android Developers. Available at: https://developer.android.com/training/permissions/requesting.

        val perms = arrayOf(SEND_SMS, POST_NOTIFICATIONS)
        requestPermissions(perms, 8000) //Asks the user for perms

        //Loads need decay service without needing to be attached to app
        startForegroundService(Intent(this, NeedService::class.java))



        enableEdgeToEdge()

        setContent {


            //Checks if there are creatures already, shows add dialog if not
            val isEmpty by viewModel.isEmpty.collectAsState(null)

            Project309Theme {
                when (isEmpty) {
                    true  -> AddAnAnimalDialog() //Show dialog when db empty
                    false -> MainView().Show() //Show main view when there are creatures
                    null  -> return@Project309Theme //Does nothing if list still loading
                }
            }

        }
    }






}




