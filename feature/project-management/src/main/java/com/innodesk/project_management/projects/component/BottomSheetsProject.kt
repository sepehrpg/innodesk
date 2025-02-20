package com.innodesk.project_management.projects.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.extension.clickableWithNoRipple
import com.example.designsystem.component.AppBasicTextField
import com.example.designsystem.component.AppCustomLeadingIconTab
import com.example.designsystem.component.AppCustomLeadingIconTabItem
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppText
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppColorPickerLibrary1
import com.example.designsystem.component.AppFilledIconButton
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetsProject(
    isVisible: MutableState<Boolean> = mutableStateOf(false),
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    AppModalBottomSheet(
        isVisible = isVisible.value,
        onDismissRequest = { isVisible.value = false },
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            AppBottomSheetDragHandle(
                onDoneClick = {},
                onCancelClick = {},
                title = "Create Project",
                done = "Create",
                cancel = "Cancel"
            )
        },
        content = {
            BottomSheetContent()
        }
    )
}


@Composable
fun BottomSheetContent() {
    var projectName: String by remember { mutableStateOf("") }
    val tempSelected = remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var colorValue:Color? by remember { mutableStateOf(null) }

    AppColorPickerLibrary1(showDialog, onDismissRequest = {showDialog=false}, colorValue = {
        colorValue = it
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {

        item {
            Column(Modifier.padding(top = 0.dp)) {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    AppText("Project Name", fontSize = 14.sp, color = Color.DarkGray)
                }

                Box(Modifier.fillMaxWidth().padding(horizontal = 5.dp)){
                    AppOutlineTextFieldStatic1(
                        value = projectName,
                        placeHolder = "Enter project name",
                        onValueChange = {
                            projectName = it
                        }
                    )
                }
            }
        }


        item {
            Column(Modifier.padding(top = 20.dp)) {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    AppText("Access", fontSize = 14.sp, color = Color.DarkGray)
                }
                val list = listOf(
                    /*AppCustomLeadingIconTabItem(
                        id = 1,
                        description = "A private, single-user project visible only to you.",
                        text = { Text("Personal", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        selected = tempSelected,
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Person,
                                contentDescription = "Home Icon"
                            )
                        },
                    ),*/
                    AppCustomLeadingIconTabItem(id = 2,
                        description = "Collaborate by inviting specific people via link or direct invitations. Only invited users can view and access the project.",
                        additionalUi = true,
                        selected = tempSelected,
                        text = { Text("Private", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Group,
                                contentDescription = "Home Icon"
                            )
                        }),
                    AppCustomLeadingIconTabItem(
                        id = 3,
                        description = "A shared project visible to all team members within your company.",
                        text = { Text("Company", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Groups,
                                contentDescription = "Home Icon"
                            )
                        },
                    ),
                )
                Box(Modifier.fillMaxWidth().padding(horizontal = 5.dp)) {
                    AppCustomLeadingIconTab(
                        item = list,
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            list.map { it.nonSelected() }
                            list[it].isSelected()
                        }
                    )
                }
            }
        }


        item {
            Column(Modifier.padding(top = 20.dp)) {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    AppText("Project Setting", fontSize = 14.sp, color = Color.DarkGray)
                }
                Column(Modifier.fillMaxWidth().padding(horizontal = 5.dp).clip(RoundedCornerShape(10.dp)).background(ClickUpGray2).padding(horizontal = 5.dp)){
                    Row(Modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            AppFilledIconButton(onClick = {},
                                modifier = Modifier.width(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color.White
                                )) {
                                Icon(AppIcons.Status, contentDescription = "", tint = Color.Gray)
                            }
                            Spacer(Modifier.width(10.dp))
                            AppText("Templates", fontSize = 13.sp)
                        }
                        Icon(AppIcons.ArrowRight, contentDescription = "", tint = Color.Gray)
                    }
                    HorizontalDivider(color = Color((0xFFEEEEEE)))
                    Row(Modifier.fillMaxWidth().clickableWithNoRipple {
                        showDialog = true
                    }.padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            AppFilledIconButton(onClick = {},
                                modifier = Modifier.width(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color.White
                                )) {
                                Icon(AppIcons.Colorize, contentDescription = "", tint = Color.Gray)
                            }
                            Spacer(Modifier.width(10.dp))
                            AppText("Color", fontSize = 13.sp)
                        }

                        if (colorValue!=null){
                            Box(Modifier.size(25.dp).background(color = colorValue!!, shape = RoundedCornerShape(5.dp)))
                        }
                        else{
                            Icon(AppIcons.Add, contentDescription = "", tint = Color.Gray)
                        }
                    }
                }


                /*repeat(3) {
                    ProjectTemplateItem(if (it == 1) true else false)
                }*/

            }
        }
        item { Spacer(Modifier.height(10.dp)) }

    }
}


@Composable
private fun ProjectTemplateItem(checked: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                /*Box(){
                    if (checked){
                        Icon(AppIcons.Done, contentDescription = "Folder", tint = PrimaryColor)
                    }
                    else{
                        Icon(AppIcons.Folder, contentDescription = "Folder", tint = Color.Transparent)
                    }
                }*/
                Spacer(Modifier.width(5.dp))
                Box(Modifier.weight(1f)) {
                    AppText("Kanban", color = Color.Gray)
                }
                Spacer(Modifier.width(5.dp))
                Box() {
                    Icon(AppIcons.MoreHoriz, contentDescription = "MoreHoriz", tint = Color.Gray)
                }
            }
        }
        HorizontalDivider(thickness = 0.1.dp, color = Color(0xFFEEEEEE))
    }
}