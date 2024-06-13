package com.example.itba.hci


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.itba.hci.components.NavigationBar

@Composable
fun HomeDomeApp() {
    FruitSaladTheme {
        Scaffold(
            topBar = { FruitSaladTopAppBar() },
            bottomBar = { NavigationBar() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}


@Preview
@Composable
fun HomeDomeAppPreview() {
    HomeDomeApp()
}