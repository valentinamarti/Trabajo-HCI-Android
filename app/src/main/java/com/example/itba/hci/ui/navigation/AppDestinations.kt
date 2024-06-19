package com.example.itba.hci.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.itba.hci.R


enum class AppDestinations(@StringRes val title: Int, val icon: ImageVector, val route: String) {
    ROUTINES(R.string.bottom_navigation_routines, Icons.Outlined.Checklist, "routine_screen"),
    DEVICES(R.string.bottom_navigation_devices, Icons.Outlined.Devices, "devices_screen"),
    HOME(R.string.bottom_navigation_home, Icons.Filled.Home, "home_screen")
}