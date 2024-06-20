package com.example.itba.hci.ui.components.cards

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun RoutineCard(
    text: String,
    modifier: Modifier = Modifier,
    secondaryText: String?,
    backgroundColor: Color,
    iconColor: Color
) {
    val mediumPadding = dimensionResource(R.dimen.medium_padding)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var isPressed by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(160.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = mediumPadding, horizontal = 16.dp)
                    .widthIn(min = 192.dp, max = screenWidth)
            ){
                Icon(
                    painter = painterResource(id = if (isPressed) R.drawable.heart else R.drawable.heart_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isPressed = !isPressed },
                    tint = iconColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = if (!isPlaying) R.drawable.play_icon else R.drawable.pause_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                            .clickable { isPlaying = !isPlaying },
                        tint = iconColor
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 24.dp)
                    .widthIn(min = 192.dp, max = screenWidth)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    modifier = Modifier.weight(1f) // Para llenar el espacio disponible
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 24.dp)
                    .widthIn(min = 192.dp, max = screenWidth)
            ){
                Text(
                    text = secondaryText ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RoutineCardPreview() {

    HomeDomeTheme {
        val smallPadding = dimensionResource(R.dimen.small_padding)
        RoutineCard(
            text = "Routines1",
            secondaryText = "Routines1 description",
            modifier = Modifier.padding(smallPadding),
            backgroundColor = MaterialTheme.colorScheme.background,
            iconColor = MaterialTheme.colorScheme.primary
        )
    }
}

