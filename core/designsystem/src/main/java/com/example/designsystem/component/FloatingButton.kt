package com.example.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// App Floating Action Button
//..................................................................................................
@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = FloatingActionButtonDefaults.shape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        elevation = elevation,
        content = content,
    )
}
//..................................................................................................


// App Extended Floating Action Button
//..................................................................................................
@Composable
fun AppExtendedFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = FloatingActionButtonDefaults.shape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        elevation = elevation,
        icon = icon,
        text = text,
    )
}
//..................................................................................................



@Preview(showBackground = true,showSystemUi = true, locale = "en")
@Composable
private fun AppFloatingPreview(){
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)){
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                AppFloatingActionButton(
                    onClick = { /* Do something */ },
                    content = { Icon(Icons.Default.Add, contentDescription = null) }
                )
                AppExtendedFloatingActionButton(
                    onClick = { /* Do something */ },
                    icon = { Icon(Icons.Default.Check, contentDescription = null) },
                    text = { Text("Extended FAB") }
                )
            }
        }
    }
}