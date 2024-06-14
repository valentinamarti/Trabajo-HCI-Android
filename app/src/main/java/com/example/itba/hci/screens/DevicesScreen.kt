package com.example.itba.hci.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.itba.hci.components.cards.DeviceCard
import com.example.itba.hci.ui.theme.screenTitle

data class Device(val text: Int, val icon: Int?, val backgroundColor: Color, val iconColor: Color)
@Composable
fun DevicesScreen(navController: NavController, paddingValues: PaddingValues) {
    val devices = listOf(
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device1, R.drawable.fridge_outline, Color(0xFFFCD59D),Color(0xFFFF5722)),
        Device(R.string.device2, R.drawable.door, Color(0xFFBFE3FF),Color(0xFF2196F3))
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_navigation_devices),
            style = screenTitle,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(devices) { device ->
                DeviceCard(
                    text = device.text,
                    icon = device.icon,
                    backgroundColor = device.backgroundColor,
                    iconColor = device.iconColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesScreenPreview() {
    DevicesScreen(navController = rememberNavController() ,paddingValues = PaddingValues(0.dp))
}