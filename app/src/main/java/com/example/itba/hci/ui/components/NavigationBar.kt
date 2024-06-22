package com.example.itba.hci.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.itba.hci.utils.navigation.AppDestinations



@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val screens = listOf(
        AppDestinations.DEVICES,
        AppDestinations.HOME,
        AppDestinations.ROUTINES
    )


    Box(
        modifier = modifier
            .height(80.dp) // Ajusta el alto según tus necesidades
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height(90.dp)
        ) {
                screens.forEach{ screen ->
                    CustomNavigationBarItem(
                        label = { Text(text = stringResource(screen.title)) },
                        icon = screen.icon,
                        selected = currentRoute == screen.route,
                        onClick = { onNavigateToRoute(screen.route) },
                        isHome = screen.route == AppDestinations.HOME.route
                    )
                }
            }

    }

}

@Composable
fun CircleIcon(
    icon: ImageVector,
    contentDescription: String?,
    selected: Boolean,
    size: Dp = 40.dp,
    isHome: Boolean = false
    ) {
    Surface(
        modifier = Modifier
            .size(size)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor = Color.Black.copy(alpha = 0.4f), // Ajusta la opacidad del sombreado
            ),
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.onSecondary else if(isHome) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.background,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size * 0.9f),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(size * 0.6f)
            )
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    isHome: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .offset(y = if (isHome) (-20).dp else 4.dp)
    ) {
        val size = if (isHome) 60.dp else 40.dp
        CircleIcon(icon = icon, contentDescription = null, selected = selected, size = size, isHome = isHome)
        Spacer(modifier = Modifier.height(2.dp))
        label()
    }
}


