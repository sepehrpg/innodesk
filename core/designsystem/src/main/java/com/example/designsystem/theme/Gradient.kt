package com.example.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model gradient color values for Now in Android.
 *
 * @param top The top gradient color to be rendered.
 * @param bottom The bottom gradient color to be rendered.
 * @param container The container gradient color over which the gradient will be rendered.
 */
@Immutable
data class GradientColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)

/**
 * A composition local for [GradientColors].
 * staticCompositionLocalOf : instead of passing value as parameter in composable function you can define
 * this (for static value that don't need to recompose) or compositionLocalOf for dynamic value
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }
