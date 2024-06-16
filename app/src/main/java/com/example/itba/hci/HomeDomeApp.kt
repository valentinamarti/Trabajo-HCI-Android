package com.example.itba.hci


import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.itba.hci.ui.components.NavigationBar
import com.example.itba.hci.ui.components.TopAppBar
import com.example.itba.hci.ui.theme.HomeDomeTheme
import com.example.itba.hci.utils.NavigationBarGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeDomeApp() {
    val navController = rememberNavController()
    HomeDomeTheme {
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { NavigationBar(navController = navController) }
        ) { innerPadding ->
            NavigationBarGraph(navController = navController, paddingValues = innerPadding)
        }
    }
}

@Preview
@Composable
fun HomeDomeAppPreview() {
    HomeDomeApp()
}
