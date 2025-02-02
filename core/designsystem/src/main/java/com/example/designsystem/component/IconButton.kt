
package com.example.designsystem.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppTheme


@Composable
fun NiaIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon,
) {
    // TODO: File bug
    // Can't use regular IconToggleButton as it doesn't include a shape (appears square)
    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (checked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = NiaIconButtonDefaults.DISABLED_ICON_BUTTON_CONTAINER_ALPHA,
                )
            } else {
                Color.Transparent
            },
        ),
    ) {
        if (checked) checkedIcon() else icon()
    }
}






@ThemePreviews
@Composable
fun IconButtonPreview() {
    AppTheme {
        NiaIconToggleButton(
            checked = true,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = AppIcons.BookmarkBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = AppIcons.Bookmark,
                    contentDescription = null,
                )
            },
        )
    }
}

@ThemePreviews
@Composable
fun IconButtonPreviewUnchecked() {
    AppTheme {
        NiaIconToggleButton(
            checked = false,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = AppIcons.BookmarkBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = AppIcons.Bookmark,
                    contentDescription = null,
                )
            },
        )
    }
}

/**
 * Now in Android icon button default values.
 */
object NiaIconButtonDefaults {
    // TODO: File bug
    // IconToggleButton disabled container alpha not exposed by IconButtonDefaults
    const val DISABLED_ICON_BUTTON_CONTAINER_ALPHA = 0.12f
}
