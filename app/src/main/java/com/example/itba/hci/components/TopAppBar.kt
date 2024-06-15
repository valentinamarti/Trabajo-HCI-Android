package com.example.itba.hci.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier,
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.homedome),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 16.dp)
            )
        },
        actions = {
            IconButton(
                onClick = { /* Handle notifications click */ },
                modifier = Modifier
                    .padding(end = 12.dp))
            {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint =  MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.size(28.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeDomeTopAppBarPreview() {
    HomeDomeTheme {
        TopAppBar()
    }
}