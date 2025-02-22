package com.innodesk.project_management.projects.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppDeleteDialog
import com.example.designsystem.component.AppElevatedButtonWithIcon
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.innodesk.project_management.utils.TypeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddTemplate(
    isVisible: MutableState<Boolean> = mutableStateOf(false),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    typeScreen: TypeScreen = TypeScreen.DEFAULT,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {
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
                title = if(typeScreen==TypeScreen.EDIT)  "Edit Template" else "Add Template",
                done = "Done",
                cancel = "Cancel",
            )
        },
        content = {
            BottomSheetContent(typeThisScreen=typeScreen)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent(typeThisScreen:TypeScreen) {
    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog){
        AppDeleteDialog(onDismissRequest = { openDeleteDialog=false }, properties = DialogProperties() , onDelete = {} )
    }

    var openBottomSheet = remember { mutableStateOf(false) }
    var typeScreen by remember { mutableStateOf(TypeScreen.ADD) }
    if (openBottomSheet.value) {
        BottomSheetAddStatus(isVisible = openBottomSheet, onCancelClick = {
            openBottomSheet.value = false
        }, onDoneClick = {

        }, typeScreen = typeScreen)
    }

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                var projectName: String by remember { mutableStateOf("") }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 5.dp)
                ) {
                    AppOutlineTextFieldStatic1(
                        value = projectName,
                        placeHolder = "Enter template name",
                        onValueChange = {
                            projectName = it
                        }
                    )
                }
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            }

            item {
                Column(Modifier.padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 15.dp)) {
                    AppText("Project Statuses", fontSize = 14.sp, color = Color.Black)
                    Spacer(Modifier.height(3.dp))
                    AppText(
                        "Add statuses that represent the progress of your tasks",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            if (typeThisScreen==TypeScreen.EDIT){
                items(3) {
                    StatusesItem(it, onMenuClick = {
                        typeScreen = TypeScreen.EDIT
                        openBottomSheet.value = true
                    })
                }
            }


            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 10.dp)
                ) {
                    AppElevatedButtonWithIcon(
                        onClick = {
                            typeScreen = TypeScreen.ADD
                            openBottomSheet.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation=0.dp, pressedElevation =2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ClickUpGray4,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        content = {
                            Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically){
                                AppIcon(AppIcons.Add, contentDescription = null, tint = Color.Gray)
                                Spacer(Modifier.width(5.dp))
                                AppText("Add Status", fontSize = 14.sp, modifier = Modifier.padding(top = 3.dp))
                            }
                        }
                    )
                }
            }

            if (typeThisScreen==TypeScreen.EDIT){
                item{
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 5.dp)
                    ) {
                        AppElevatedButtonWithIcon(
                            onClick = { openDeleteDialog = true },
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation=0.dp, pressedElevation =2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ClickUpGray4,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            content = {
                                Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically){
                                    AppIcon(AppIcons.Delete, contentDescription = null, tint = ClickUpPink1)
                                    Spacer(Modifier.width(5.dp))
                                    AppText("Delete Template", fontSize = 14.sp, modifier = Modifier.padding(top = 3.dp), color = ClickUpPink1)
                                }
                            }
                        )
                    }
                }
            }


            item { Spacer(Modifier.height(30.dp)) }
        }
    }
}


@Composable
private fun StatusesItem(index: Int,onMenuClick:()->Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 5.dp)
            .border(
                border = BorderStroke(1.dp, color = Color(0xFFEEEEEE)),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (index == 0) {
                Spacer(Modifier.width(5.dp))
            } else {
                AppIcon(AppIcons.DragIndicator, contentDescription = "", tint = Color.Gray)
                Spacer(Modifier.width(10.dp))
            }
            Box(
                Modifier
                    .size(16.dp)
                    .background(
                        color = if (index == 0) Color.Gray else Color.Blue,
                        shape = RoundedCornerShape(5.dp)
                    )
            )

            Spacer(Modifier.width(10.dp))
            if (index == 0) {
                AppText(
                    "Open",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 3.dp)
                )
            } else {
                AppText(
                    "In Progress",
                    fontSize = 14.sp,
                    color = Color.Blue,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }


            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                AppIcon(AppIcons.MoreHoriz, contentDescription = "", tint = Color.Gray, modifier = Modifier.clickableWithNoRipple {
                    onMenuClick()
                })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BottomSheetAddTemplatePreview() {
    var open = remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            open.value = true
        }) {
            Text("OpenBottomSheet")
        }
        BottomSheetAddTemplate(
            isVisible = open,
            /*sheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Expanded,
                skipHiddenState = false
            ),*/
            onCancelClick = {}, onDoneClick = {})
    }

}