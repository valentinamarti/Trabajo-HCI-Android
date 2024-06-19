package com.example.itba.hci.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.components.cards.RoutineCard
import com.example.itba.hci.ui.theme.screenTitle


data class Routine(val text: Int, val secondaryText: Int, val backgroundColor: Color, val iconColor: Color)
@Composable
fun RoutinesScreen() {
    val routines = listOf(
        Routine(R.string.routine1, R.string.routine1,Color(0xFFFCD59D), Color(0xFFFF5722)),
        Routine(R.string.routine1, R.string.routine1, Color(0xFFFCD59D), Color(0xFFFF5722)),
        Routine(R.string.routine1,  R.string.routine1, Color(0xFFFCD59D), Color(0xFFFF5722)),
        Routine(R.string.routine2,  R.string.routine1, Color(0xFFFCD59D), Color(0xFFFF5722)),
        Routine(R.string.routine2,  R.string.routine1, Color(0xFFFCD59D), Color(0xFFFF5722)),
        Routine(R.string.routine2,  R.string.routine1, Color(0xFFBFE3FF), Color(0xFF2196F3))
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_navigation_routines),
            style = screenTitle,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(routines) { routine ->
                RoutineCard(
                    text = routine.text,
                    secondaryText = routine.secondaryText,
                    backgroundColor = routine.backgroundColor,
                    iconColor = routine.iconColor,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RoutinesScreenPreview() {
//    DevicesScreen(navController = rememberNavController() ,paddingValues = PaddingValues(0.dp))
//}