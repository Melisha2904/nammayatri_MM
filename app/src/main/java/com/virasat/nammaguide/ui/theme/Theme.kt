package com.virasat.nammaguide.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary          = TempleGold,
    onPrimary        = DarkBrown,
    primaryContainer = StoneBrown,
    onPrimaryContainer = AncientIvory,
    secondary        = WarmSaffron,
    onSecondary      = DarkBrown,
    secondaryContainer = DeepTerracotta,
    onSecondaryContainer = AncientIvory,
    tertiary         = MossGreen,
    background       = DarkBrown,
    onBackground     = IvoryText,
    surface          = DarkSurface,
    onSurface        = IvoryText,
    surfaceVariant   = CardSurface,
    onSurfaceVariant = AncientIvory,
    outline          = TempleGold,
    error            = AlertRed
)

private val LightColorScheme = lightColorScheme(
    primary          = DeepTerracotta,
    onPrimary        = AncientIvory,
    primaryContainer = LightCard,
    onPrimaryContainer = BrownText,
    secondary        = WarmSaffron,
    onSecondary      = AncientIvory,
    secondaryContainer = LightSurface,
    onSecondaryContainer = BrownText,
    tertiary         = MossGreen,
    background       = LightSurface,
    onBackground     = BrownText,
    surface          = AncientIvory,
    onSurface        = BrownText,
    surfaceVariant   = LightCard,
    onSurfaceVariant = StoneBrown,
    outline          = DeepTerracotta,
    error            = AlertRed
)

@Composable
fun VirastatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = VirastatTypography,
        content     = content
    )
}
