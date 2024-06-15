package com.example.itba.hci.components.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun RoutineCard(
    @DrawableRes iconDrawable: Int? = null,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    @StringRes secondaryText: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val smallPadding = dimensionResource(R.dimen.small_padding)

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.width(160.dp) // Ancho de la tarjeta
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(160.dp)
            ) {
                Text(
                    text = stringResource(text),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = mediumPadding)
                )
                iconDrawable?.let { painterResource(it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(24.dp)
                    )
                }
        }
            Text(
                text = stringResource(secondaryText),
                style = MaterialTheme.typography.titleSmall, // Estilo para el texto secundario
                modifier = Modifier.padding(
                    start = mediumPadding,
                    top = smallPadding,
                    end = mediumPadding,
                    bottom = mediumPadding
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineCardPreview() {
    val smallPadding = dimensionResource(R.dimen.small_padding)

    HomeDomeTheme {
        val smallPadding = dimensionResource(R.dimen.small_padding)
        RoutineCard(
            iconDrawable = R.drawable.play_icon,
            text = R.string.bottom_navigation_routines,
            secondaryText = R.string.bottom_navigation_routines,
            modifier = Modifier.padding(smallPadding)
        )
    }
}
