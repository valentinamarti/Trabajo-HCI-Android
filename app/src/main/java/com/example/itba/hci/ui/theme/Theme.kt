package com.example.itba.hci.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFECF0F1),
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFE8F3FC),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFC3DBFF),
    secondary = Color.White,
    tertiary = Color.White,
    background = Color(0xFFE8F3FC),
    onPrimary = Color(0xFF244388),
    onSecondary = Color(0xFF3E7EF7),

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

Aquí están los colores convertidos al formato de 32 bits 0xAARRGGBB:

white: #fff

Hex: #ffffff
0xFFFFFFFF
background-blue: #e8f3fc

0xFFE8F3FC
blue-hover: #b3d9f9

0xFFB3D9F9
lightblue: #c3dbff

0xFFC3DBFF
blue: #4378f9

0xFF4378F9
lightorange: #ffe7d5

0xFFFFE7D5
orange: #eb843a

0xFFEB843A
lightpurple: #e8c3ff

0xFFE8C3FF
purple: #9747ff

0xFF9747FF
black: #000000

0xFF000000
button-blue: #abc8fe

0xFFABC8FE
dark-blue: #3054aa

0xFF3054AA



    */
)

@Composable
fun HomeDomeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )

}