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
import com.example.itba.hci.R
import com.example.itba.hci.model.Device
import com.example.itba.hci.model.DeviceType
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.screens.toColor

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
    doorViewModel: DoorViewModel,
    fridgeViewModel: FridgeViewModel,
    speakerViewModel: SpeakerViewModel,
    blindViewModel: BlindViewModel
) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val icon = when(device.type) {
        DeviceType.SPEAKER -> R.drawable.speaker
        DeviceType.FRIDGE -> R.drawable.fridge_outline
        DeviceType.BLIND -> R.drawable.blinds
        DeviceType.DOOR -> R.drawable.door
        else -> R.drawable.homedome
    }


    var isPressed by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(device.meta!!.favorite) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = mediumPadding, horizontal = 16.dp)
                .widthIn(min = 192.dp, max = screenWidth)
        ) {
            device.meta?.color?.primary?.let {
                Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(it.toColor())
            }?.let {
                Box(
                    modifier = it,
                    contentAlignment = Alignment.Center
                ) {
                    device.meta!!.color.secondary?.let { it1 ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp),
                            tint = it1.toColor()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(mediumPadding))
            Text(
                text = device.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            device.meta?.color?.secondary?.let {
                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.heart else R.drawable.heart_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isFavorite = !isFavorite
                            device.id?.let { it1 ->
                                when (device.type) {
                                    DeviceType.BLIND -> blindViewModel.updateFav(it1)
                                    DeviceType.DOOR -> doorViewModel.updateFav(it1)
                                    DeviceType.FRIDGE -> fridgeViewModel.updateFav(it1)
                                    DeviceType.SPEAKER -> speakerViewModel.updateFav(it1)
                                    else -> {}
                                }
                            }
                        },
                    tint = it.toColor()
                )
            }
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
