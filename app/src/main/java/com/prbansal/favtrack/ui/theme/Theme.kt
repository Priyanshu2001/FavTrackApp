package com.prbansal.favtrack.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = ColorPrimary,
    primaryVariant = ColorPrimaryVariant,
    secondary = ColorSecondary,
    background = ColorPrimaryDarkVariant,
    surface = ColorSecondary,
    onPrimary = ColorVariant,
    onSecondary = ColorOnPrimaryVariant,
    onBackground = ColorVariant,
    onSurface = ColorPrimary,
)

@Composable
fun FavTrackTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primaryVariant.toArgb()
//            window.navigationBarColor = colors.secondary.toArgb()
//
//            WindowCompat.getInsetsController(window, view)
//                ?.isAppearanceLightStatusBars = darkTheme
//            WindowCompat.getInsetsController(window, view)
//                ?.isAppearanceLightNavigationBars = darkTheme
        }
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}