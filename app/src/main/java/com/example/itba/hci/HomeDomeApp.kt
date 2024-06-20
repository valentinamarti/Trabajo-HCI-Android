package com.example.itba.hci


import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.itba.hci.ui.components.NavigationBar
import com.example.itba.hci.ui.components.TopAppBar
import com.example.itba.hci.utils.navigation.AppNavGraph
import com.example.itba.hci.ui.theme.HomeDomeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeDomeApp() {
    val navController = rememberNavController()
    HomeDomeTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { NavigationBar(currentRoute = currentRoute){ route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } }
        ) {innerPadding ->
            AppNavGraph(navController = navController,  innerPadding = innerPadding)
        }
    }
}

@Preview
@Composable
fun HomeDomeAppPreview() {
    HomeDomeApp()
}
