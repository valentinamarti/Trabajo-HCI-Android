package com.example.itba.hci.components.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(192.dp)
                .padding(mediumPadding)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.width(mediumPadding))
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavouriteCardPreview() {
    val smallPadding = dimensionResource(R.dimen.small_padding)

    HomeDomeTheme {
        DeviceCard(
            text = R.string.app_name,
            modifier = Modifier.padding(smallPadding),
            icon = R.drawable.devices
        )
    }
}