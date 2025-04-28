package com.innodesk.project_management.workspace.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppColorPickerLibrary1
import com.example.designsystem.component.AppDeleteDialog
import com.example.designsystem.component.AppElevatedButtonWithIcon
import com.example.designsystem.component.AppFilledIconButton
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.extension.hexStringToColor
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.innodesk.project_management.templates_statuses.statuses.screen.TemplateStatusScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWorkSpaceStatusMenu(
    isVisible: Boolean,
    templatesStatusEntity: TemplatesStatusEntity? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    onDismissRequest: ()-> Unit,
    onChangeTemplateStatusName : (String) -> Unit,
    onChangeTemplateStatusColor : (Color) -> Unit,
    onDelete : (TemplatesStatusEntity?) -> Unit,
){
    var templateStatusName: String by remember { mutableStateOf(templatesStatusEntity?.name?:"") }

    var showDialog by remember { mutableStateOf(false) }
    var colorValue: Color? by remember {
        mutableStateOf(
            if (templatesStatusEntity?.color != null) Color.hexStringToColor(templatesStatusEntity.color!!) else null
        )
    }
    AppColorPickerLibrary1(showDialog, onDismissRequest = {showDialog=false}, colorValue = {
        colorValue = it
        onChangeTemplateStatusColor(it)
    })


    AppModalBottomSheet(
        isVisible = isVisible,
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            AppBottomSheetDragHandle(
                onDoneClick = onDoneClick,
                onCancelClick = onCancelClick,
                title = if(templatesStatusEntity!=null)  "Edit Status" else "Add Status",
                done = "Done",
                cancel = "Cancel",
            )
        },
        content = {
            var openDeleteDialog by remember { mutableStateOf(false) }
            if (openDeleteDialog) {
                AppDeleteDialog(
                    onDismissRequest = { openDeleteDialog = false },
                    properties = DialogProperties(),
                    onDelete = {
                        onDelete(templatesStatusEntity)
                        openDeleteDialog = false
                    })
            }


            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 5.dp)
                        ) {
                            AppOutlineTextFieldStatic1(
                                value = templateStatusName,
                                placeHolder = "Type status name",
                                onValueChange = {
                                    templateStatusName = it
                                    onChangeTemplateStatusName(it)
                                }
                            )
                        }
                    }


                    item {
                        Column(
                            Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 5.dp).clip(
                                RoundedCornerShape(10.dp)
                            ).background(
                                ClickUpGray2
                            ).padding(horizontal = 5.dp)){
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
                                        AppIcon(AppIcons.Colorize, contentDescription = "", tint = Color.Gray)
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    AppText("Color", fontSize = 13.sp)
                                }

                                if (colorValue!=null){
                                    Box(Modifier.size(25.dp).background(color = colorValue!!, shape = RoundedCornerShape(5.dp)))
                                }
                                else{
                                    AppIcon(AppIcons.Add, contentDescription = "", tint = Color.Gray)
                                }
                            }
                        }
                    }

                    if (templatesStatusEntity != null) {
                        item {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp, bottom = 5.dp, top = 5.dp)
                            ) {
                                AppElevatedButtonWithIcon(
                                    onClick = { openDeleteDialog = true },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp,
                                        pressedElevation = 2.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ClickUpGray4,
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    content = {
                                        Row(
                                            Modifier.padding(vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            AppIcon(
                                                AppIcons.Delete,
                                                contentDescription = null,
                                                tint = ClickUpPink1
                                            )
                                            Spacer(Modifier.width(5.dp))
                                            AppText(
                                                "Delete Template",
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(top = 3.dp),
                                                color = ClickUpPink1
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }

                    item { Spacer(Modifier.height(10.dp)) }
                }
            }
        }
    )
}