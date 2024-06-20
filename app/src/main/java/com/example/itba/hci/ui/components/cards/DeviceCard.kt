package com.example.itba.hci.ui.components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.itba.hci.R
import com.example.itba.hci.model.DeviceType

@Composable
fun DeviceCard(
    text: String,
    deviceType: DeviceType,
    primaryColor: String,
    secondaryColor: String,
    isFavourite: Boolean,
    onClick: () -> Unit
) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val icon = when(deviceType) {
        DeviceType.SPEAKER -> R.drawable.speaker
        DeviceType.FRIDGE -> R.drawable.fridge_outline
        DeviceType.BLIND -> R.drawable.blinds
        DeviceType.DOOR -> R.drawable.door
        else -> R.drawable.homedome
    }

    var isPressed by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(80.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = mediumPadding, horizontal = 16.dp)
                .widthIn(min = 192.dp, max = screenWidth)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(primaryColor.toColorInt())),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    tint = Color(secondaryColor.toColorInt())
                )
            }
            Spacer(modifier = Modifier.width(mediumPadding))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = if (isFavourite) R.drawable.heart else R.drawable.heart_outline),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isPressed = !isPressed },
                tint = Color(secondaryColor.toColorInt())
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun DeviceCardPreview() {
//    val smallPadding = dimensionResource(R.dimen.small_padding)
//
//    HomeDomeTheme {
//        DeviceCard(
//            text = R.string.bottom_navigation_devices,
//            modifier = Modifier.padding(smallPadding),
//            icon = R.drawable.devices,
//            backgroundColor = MaterialTheme.colorScheme.background,
//            iconColor = MaterialTheme.colorScheme.primary
//        )
//    }
//}
