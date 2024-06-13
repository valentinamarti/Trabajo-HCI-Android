package com.example.itba.hci.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.itba.hci.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp


@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            NavigationBarItem(
                icon = {
                    CircleIcon(icon = Icons.Default.List, contentDescription = null)
                },
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_routines),
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                },
                selected = false,
                onClick = {}
            )
            NavigationBarItem(
                icon = {
                    CircleIcon(icon = Icons.Default.Home, contentDescription = null)
                },
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_home),
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                },
                selected = true,
                onClick = {}
            )
            NavigationBarItem(

                icon = {
                    CircleIcon(icon = Icons.Default.Person, contentDescription = null)
                },
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_devices),
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                },
                selected = false,
                onClick = {}
            )
        }
    }
}



@Composable
fun CircleIcon(icon: ImageVector, contentDescription: String?) {
    Surface(
        modifier = Modifier.size(40.dp), // Tamaño del círculo exterior
        shape = CircleShape,
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 36.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

