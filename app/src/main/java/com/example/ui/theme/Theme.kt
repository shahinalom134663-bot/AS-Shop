package com.example.ui.theme

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
    primary = BrandTealLight,
    onPrimary = Slate900,
    primaryContainer = BrandTealDark,
    onPrimaryContainer = BrandTealContainer,
    secondary = BrandSky,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF0369A1),
    onSecondaryContainer = BrandSkyContainer,
    tertiary = GoldEarnLight,
    onTertiary = Slate900,
    tertiaryContainer = GoldEarn,
    onTertiaryContainer = GoldEarnContainer,
    background = Slate900,
    surface = Slate800,
    onBackground = Slate100,
    onSurface = Slate100,
    surfaceVariant = Slate700,
    onSurfaceVariant = Slate300,
    outline = Slate600
)

private val LightColorScheme = lightColorScheme(
    primary = BrandTeal,
    onPrimary = Color.White,
    primaryContainer = BrandTealContainer,
    onPrimaryContainer = OnBrandTealContainer,
    secondary = BrandSky,
    onSecondary = Color.White,
    secondaryContainer = BrandSkyContainer,
    onSecondaryContainer = OnBrandSkyContainer,
    tertiary = GoldEarn,
    onTertiary = Color.White,
    tertiaryContainer = GoldEarnContainer,
    onTertiaryContainer = OnGoldEarnContainer,
    background = Slate50,
    surface = Color.White,
    onBackground = Slate900,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate600,
    outline = Slate300
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
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
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

