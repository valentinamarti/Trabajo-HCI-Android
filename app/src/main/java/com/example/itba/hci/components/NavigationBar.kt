package com.example.itba.hci.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.itba.hci.utils.NavigationScreen

@Composable
fun NavigationBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val screens = listOf(
        NavigationScreen.Home,
        NavigationScreen.Routines,
        NavigationScreen.Devices,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            screens.forEach{ screen ->
                CustomNavigationBarItem(
                label = {
                    Text(text = screen.title)
                },
                icon = screen.icon,
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route)
                })
            }
        }
    }
}

@Composable
fun CircleIcon(
    icon: ImageVector,
    contentDescription: String?,
    selected: Boolean
    ) {
    Surface(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.background,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    containerColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = containerColor,
        shadowElevation = 8.dp,
        modifier = modifier
            .height(80.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
        ) {
            content()
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        CircleIcon(icon = icon, contentDescription = null, selected = selected)
        Spacer(modifier = Modifier.height(4.dp))
        label()
    }
}


