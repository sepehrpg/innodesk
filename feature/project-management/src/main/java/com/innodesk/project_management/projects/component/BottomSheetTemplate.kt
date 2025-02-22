package com.innodesk.project_management.projects.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppCustomSearchBarBasicTextField
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppIconButton
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpWhiteBackground2
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.utils.TypeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTemplate(
    isVisible: MutableState<Boolean> = mutableStateOf(false),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {

    var search by remember { mutableStateOf("") }

    AppModalBottomSheet(
        isVisible = isVisible.value,
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = { isVisible.value = false },
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            AppBottomSheetDragHandle(
                onDoneClick = {},
                onCancelClick = onCancelClick,
                title = "Template",
                done = "Done",
                cancel = "Cancel",
                content = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp)) {
                        Spacer(Modifier.height(15.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)) {
                            AppCustomSearchBarBasicTextField(
                                value = search,
                                onValueChange = {},
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        ClickUpWhiteBackground2, ClickUpWhiteBackground2
                                    )
                                ),
                                shadowValue = 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                        Spacer(Modifier.height(15.dp))
                    }
                }
            )
        },
        content = {
            BottomSheetContent()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent() {
    var openBottomSheet = remember { mutableStateOf(false) }
    var typeScreen by remember { mutableStateOf(TypeScreen.ADD) }
    if (openBottomSheet.value) {
        BottomSheetAddTemplate(isVisible = openBottomSheet, onCancelClick = {
            openBottomSheet.value = false
        }, onDoneClick = {

        }, typeScreen = typeScreen)
    }

    Column {
        HorizontalDivider(color = ClickUpGray4, thickness = 15.dp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            item {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppText("Template", fontSize = 14.sp, color = Color.Gray)
                        Box(contentAlignment = Alignment.CenterEnd) {
                            AppIconButton(onClick = {
                                typeScreen = TypeScreen.ADD
                                openBottomSheet.value = true
                            }) {
                                AppIcon(AppIcons.Add, tint = Color.Gray, contentDescription = "")
                            }
                        }
                    }
                    HorizontalDivider(
                        Modifier.padding(top = 0.dp),
                        color = Color(0xFFEEEEEE),
                        thickness = 1.dp
                    )
                }
            }
            items(2) {
                TemplateItem(it, onMenuClick = {
                    typeScreen = TypeScreen.EDIT
                    openBottomSheet.value = true
                })
            }
            item { Spacer(Modifier.height(40.dp)) }

        }
    }

}


@Composable
private fun TemplateItem(index: Int,onMenuClick:()->Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(10.dp))
            Box(
                Modifier.sizeIn(minWidth = 25.dp),
                contentAlignment = Alignment.Center
            ) {
                if (index == 0) {
                    AppIcon(AppIcons.Done, contentDescription = "", tint = PrimaryColor)
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(4f)) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 17.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Box() {
                            AppIcon(
                                AppIcons.Folder,
                                contentDescription = "Folder",
                                tint = Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(Modifier.width(5.dp))
                        Box(Modifier.weight(1f)) {
                            AppText("Kanban", fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(Modifier.width(5.dp))
                        Box(Modifier.clickableWithNoRipple {
                            onMenuClick()
                        }) {
                            AppIcon(
                                AppIcons.MoreHoriz,
                                contentDescription = "MoreHoriz",
                                tint = Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFEEEEEE))
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BottomSheetTemplatePreview() {
    var open = remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            open.value = true
        }) {
            Text("OpenBottomSheet")
        }

        BottomSheetTemplate(
            isVisible = open,
            sheetState = rememberStandardBottomSheetState(),
            onCancelClick = {},
            onDoneClick = {})
    }
}