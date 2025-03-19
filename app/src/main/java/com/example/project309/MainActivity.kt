package com.example.project309

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.RamenDining
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project309.ui.theme.Project309Theme

class MainActivity : ComponentActivity() {
    data class NavItem(val name: String, val longName: String, val icon: ImageVector)

    val tabs = arrayOf<NavItem>(
        NavItem(
            name     = "Stats",
            longName = "Pet Statistics",
            icon     = Icons.Outlined.HealthAndSafety
        ),
        NavItem(
            name     = "Play",
            longName = "Play with Your Pet",
            icon     = Icons.Outlined.SportsEsports
        ),
        NavItem(
            name     = "Feed",
            longName = "Feed Your Pet",
            icon     = Icons.Outlined.RamenDining
        ),
        NavItem(
            name     = "Sleep",
            longName = "Send your pet to sleep",
            icon     = Icons.Outlined.Bed
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val viewModel: AnimalDataViewModel = viewModel()
            val nav = rememberNavController()
            var sel_tab by remember { mutableIntStateOf(0) }
            Project309Theme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            tabs.forEachIndexed { index, t ->
                                NavigationBarItem(
                                    label = { Text(t.name) },
                                    icon = { Icon(t.icon, t.name) },
                                    selected = sel_tab == index,
                                    onClick = {
                                        sel_tab = index
                                        nav.navigate(t.name)
                                    }
                                )
                            }

                        }
                    },
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(tabs[sel_tab].longName) }
                        )
                    }

                ) { p ->
                    NavHost(
                        nav,
                        startDestination = tabs[0].name,
                        Modifier.padding(p),
                    ) {

                        composable("Stats") { StatsView(viewModel) }

                        composable("Play") {
                            AnimalAndIconView(
                                viewModel,
                                arrayOf(
                                    "⚾",
                                    "🎮"
                                ),

                            )
                        }

                        composable("Feed") {
                            AnimalAndIconView(
                                viewModel,
                                arrayOf(
                                    "\uD83C\uDF55"
                                )
                            )
                        }

                        composable("Sleep"){
                            SleepView(viewModel)
                        }

                    }
                }
            }
        }
    }
}




