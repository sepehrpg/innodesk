package com.innodesk.project_management.workspace.component

import android.widget.Space
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.Output
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppColorPickerLibrary1
import com.example.designsystem.component.AppDeleteButton
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
import com.example.designsystem.theme.ClickUpGray1
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpGray5
import com.example.designsystem.theme.ClickUpPink1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWorkSpaceMenu(
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
                title = "Settings",
                done = "Done",
                cancel = "Cancel",
            )
        },
        content = { BottomSheetContent() }
    )
}


@Composable
private fun BottomSheetContent(){
    var templateStatusName: String by remember { mutableStateOf("App") }

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 18.dp, vertical = 7.dp)
        ) {

            item {
                AppText("Project Name", color = Color.Gray, fontSize = 13.sp)
                Spacer(Modifier.height(2.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    AppOutlineTextFieldStatic1(
                        value = templateStatusName,
                        placeHolder = "Project Name",
                        onValueChange = {
                            templateStatusName = it
                        }
                    )
                }
            }

            item{
                Spacer(Modifier.height(15.dp))
                AppText("Actions", color = Color.Gray, fontSize = 13.sp)
                Box(Modifier.clip(RoundedCornerShape(10.dp)).background(ClickUpGray2)){
                    Column(Modifier.padding(vertical = 5.dp)){


                        Row(Modifier.fillMaxWidth().padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically){
                            AppFilledIconButton(
                                onClick = {},
                                modifier = Modifier.width(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color.White
                                )
                            ) {
                                AppIcon(AppIcons.CopyAll, contentDescription = "CopyAll", tint = Color.Gray)
                            }
                            Spacer(Modifier.width(7.dp))
                            AppText("Duplicate", color = Color.DarkGray, fontSize = 13.sp)
                        }

                        HorizontalDivider(color = Color(0xFFEEEEEE), modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
                        Row(Modifier.fillMaxWidth().padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically){
                            AppFilledIconButton(
                                onClick = {},
                                modifier = Modifier.width(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color.White
                                )
                            ){
                                AppIcon(AppIcons.StarBorder, contentDescription = "StarBorder", tint = Color.Gray)
                            }
                            Spacer(Modifier.width(7.dp))
                            AppText("Add To Favorites", color = Color.DarkGray, fontSize = 13.sp)
                        }
                        HorizontalDivider(color = Color(0xFFEEEEEE), modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
                        Row(Modifier.fillMaxWidth().padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically){
                            AppFilledIconButton(
                                onClick = {},
                                modifier = Modifier.width(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color.White
                                )
                            ) {
                                AppIcon(AppIcons.Archive, contentDescription = "Archive", tint = Color.Gray)
                            }
                            Spacer(Modifier.width(7.dp))
                            AppText("Archive", color = Color.DarkGray, fontSize = 13.sp)
                        }
                    }
                }

            }


            item {
                Column(Modifier.padding(top = 20.dp)) {
                    AppText("Project Settings", color = Color.Gray, fontSize = 13.sp)

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ClickUpGray2)
                    ) {

                        if (true) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickableWithNoRipple {
                                        //openBottomSheet = true
                                    }
                                    .padding(vertical = 5.dp, horizontal = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AppFilledIconButton(
                                        onClick = {

                                        },
                                        modifier = Modifier.width(40.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = Color.White
                                        )
                                    ) {
                                        AppIcon(
                                            AppIcons.Status,
                                            contentDescription = "",
                                            tint = Color.Gray
                                        )
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    AppText("Templates", fontSize = 13.sp, color = Color.DarkGray)
                                }
                                AppIcon(
                                    //if (uiState?.templateId != null) AppIcons.Done else AppIcons.ArrowRight,
                                    AppIcons.ArrowRight,
                                    contentDescription = "",
                                    //tint = if (uiState?.templateId != null) Color(0xFF003919) else Color.Gray
                                    tint =  Color.Gray
                                )
                            }
                            HorizontalDivider(color = Color(0xFFEEEEEE))
                        }


                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickableWithNoRipple {
                                    //showDialog = true
                                }
                                .padding(vertical = 5.dp, horizontal = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppFilledIconButton(
                                    onClick = {},
                                    modifier = Modifier.width(40.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    AppIcon(
                                        AppIcons.Colorize,
                                        contentDescription = "",
                                        tint = Color.Gray
                                    )
                                }
                                Spacer(Modifier.width(10.dp))
                                AppText("Color", fontSize = 13.sp, color = Color.DarkGray)
                            }

                            if (false) {
                                Box(
                                    Modifier
                                        .size(25.dp)
                                        .background(
                                            //color = colorValue!!,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                )
                            } else {
                                AppIcon(AppIcons.Add, contentDescription = "", tint = Color.Gray)
                            }

                        }


                    }


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickableWithNoRipple {
                                //showDialog = true
                            }
                            .padding(vertical = 5.dp, horizontal = 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        AppDeleteButton(text = "Delete"){}
                    }

                    /*repeat(3) {
                        ProjectTemplateItem(if (it == 1) true else false)
                    }*/

                }
            }

            item { Spacer(Modifier.height(10.dp)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomSheetWorkSpaceMenuPreview(){
    BottomSheetContent()
}