/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Orange20


/**
 * Now in Android filter chip with included leading checked icon as well as text content slot.
 *
 * @param selected Whether the chip is currently checked.
 * @param onSelectedChange Called when the user clicks the chip and toggles checked.
 * @param modifier Modifier to be applied to the chip.
 * @param enabled Controls the enabled state of the chip. When `false`, this chip will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The text label content.
 */
@Composable
fun AppFilterChipType2(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            )) {
                label()
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        } else {
            {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        },
        shape = CircleShape,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Orange20,
            selectedBorderColor = Color(0xFF152B76),
            disabledBorderColor = Orange20,
            disabledSelectedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            selectedBorderWidth = NiaChipDefaults.ChipBorderWidth,
            enabled = enabled,
            selected = selected
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Orange20,
            iconColor = Orange20,
            disabledContainerColor = if (selected) {
                Orange20.copy(
                    alpha = NiaChipDefaults.DISABLED_CHIP_CONTAINER_ALPHA,
                )
            }else {
                Color.Transparent
            },
            disabledLabelColor = Orange20.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            disabledLeadingIconColor = Orange20.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            selectedContainerColor = Color(0xFFE1E8FF),
            selectedLabelColor = Color(0xFF152B76),
            selectedLeadingIconColor = Color(0xFF152B76),
            containerColor = Orange20.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTAINER_ALPHA,
            )
        ),
    )
}



@Composable
fun AppFilterChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                label()
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                )
            }
        } else {
            null
        },
        shape = CircleShape,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Color.LightGray,
            selectedBorderColor = Color(0xFF152B76),
            disabledBorderColor = Color(0xFFEEEEEE),
            disabledSelectedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            selectedBorderWidth = NiaChipDefaults.ChipBorderWidth,
            enabled = enabled,
            selected = selected
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Color.Gray,
            iconColor = Color.Gray,
            disabledContainerColor = if (selected) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = NiaChipDefaults.DISABLED_CHIP_CONTAINER_ALPHA,
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = NiaChipDefaults.DISABLED_CHIP_CONTENT_ALPHA,
            ),
            selectedContainerColor = Color(0xFFE1E8FF),
            selectedLabelColor = Color(0xFF152B76),
            selectedLeadingIconColor = Color(0xFF152B76),
        ),
    )
}


@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    Column(Modifier.fillMaxSize()){
        AppFilterChip(selected = false, onSelectedChange = {}) {
            Text("Chip")
        }
        AppFilterChipType2(selected = true, onSelectedChange = {}) {
            Text("Chip")
        }
        AppFilterChipType2(selected = false, onSelectedChange = {}) {
            Text("Chip")
        }
    }
}

/**
 * Now in Android chip default values.
 */
object NiaChipDefaults {
    // TODO: File bug
    // FilterChip default values aren't exposed via FilterChipDefaults
    const val DISABLED_CHIP_CONTAINER_ALPHA = 0.12f
    const val DISABLED_CHIP_CONTENT_ALPHA = 0.38f
    val ChipBorderWidth = 1.dp
}
