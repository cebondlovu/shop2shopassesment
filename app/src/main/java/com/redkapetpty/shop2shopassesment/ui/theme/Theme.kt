package com.redkapetpty.shop2shopassesment.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
	primary = PrimaryDark,
	onPrimary = MainClr,
	secondary = SecondaryDark,
	onSecondary = SecondaryMainClr,
	background = DtPrimaryDark,
	surface = DtPrimaryLight
                                     )

val LightColorScheme = lightColorScheme(
	primary = PrimaryDark,
	onPrimary = MainClr,
	secondary = SecondaryDark,
	onSecondary = SecondaryMainClr,
	background = PrimaryLight,
	surface = SecondaryLight
                                       )

@Composable
fun RedkapetTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
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
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	             )
}
