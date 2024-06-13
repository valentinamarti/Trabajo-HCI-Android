package com.example.itba.hci.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.itba.hci.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    BottomNavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CustomNavigationBarItem(
                icon = Icons.Default.List,
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_routines),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                },
                selected = false,
                onClick = {}
            )
            CustomNavigationBarItem(
                icon = Icons.Default.Home,
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_home),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                },
                selected = true,
                onClick = {}
            )
            CustomNavigationBarItem(
                icon = Icons.Default.Person,
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_devices),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
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
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(36.dp) // Tamaño del contenido del círculo
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

@Composable
fun BottomNavigationBar(
    containerColor: Color,
    contentColor: Color,
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
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        CircleIcon(icon = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(4.dp))
        label()
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationBarPreview() {
    val smallPadding = dimensionResource(R.dimen.small_padding)
    HomeDomeTheme {
        NavigationBar(
            modifier = Modifier.padding(smallPadding)
        )
    }
}
