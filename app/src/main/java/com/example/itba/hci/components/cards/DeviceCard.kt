package com.example.itba.hci.components.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun DeviceCard(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null
) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = mediumPadding, horizontal = 16.dp)
                .widthIn(min = 192.dp, max = screenWidth)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(mediumPadding)) // Ajusta el espacio aquí
            }
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(start = mediumPadding) // Ajusta el padding aquí
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    val smallPadding = dimensionResource(R.dimen.small_padding)

    HomeDomeTheme {
        DeviceCard(
            text = R.string.bottom_navigation_devices,
            modifier = Modifier.padding(smallPadding),
            icon = R.drawable.devices
        )
    }
}