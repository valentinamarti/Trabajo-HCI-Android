package com.example.itba.hci.ui.components.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun FridgeCard() {
    Surface(shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Heladera", style = MaterialTheme.typography.bodyLarge)
                    Text("Room 1", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Icon", modifier = Modifier.padding(end = 16.dp))
            }

            TemperatureControl("Temperatura heladera", 7)
            TemperatureControl("Temperatura freezer", -2)
            InventoryControl()
        }
    }

}

@Composable
fun TemperatureControl(label: String, initialTemp: Int) {
    var temperature by remember { mutableStateOf(initialTemp) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(label, modifier = Modifier.weight(1f))
            Button(
                onClick = { temperature-- },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black
                )
            ) {
                Text("-")
            }
            Text("$temperature°C", modifier = Modifier.padding(horizontal = 8.dp))
            Button(
                onClick = { temperature++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black
                )
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun InventoryControl() {
    var items by remember { mutableStateOf(listOf("Queso crema", "Leche", "Huevos")) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .clip(shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Inventory", modifier = Modifier.padding(6.dp).fillMaxWidth(), textAlign = TextAlign.Left)
            items.forEach { item ->
                Text(item, modifier = Modifier.padding(2.dp).fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text("+")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeDomeTheme {
        val smallPadding = dimensionResource(R.dimen.small_padding)
        FridgeCard()
    }
}
