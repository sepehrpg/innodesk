package com.innodesk.project_management.templates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import com.example.designsystem.component.AppDeleteDialog
import com.example.designsystem.component.AppElevatedButtonWithIcon
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.component.AppText
import com.example.designsystem.component.dragContainer
import com.example.designsystem.component.draggableItems
import com.example.designsystem.component.rememberDragDropState
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.extension.hexStringToColor
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.innodesk.project_management.projects.ProjectsViewModel
import com.innodesk.project_management.templates.component.BottomSheetTemplateStatus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateUpsertScreen(
    viewModel: ProjectsViewModel = hiltViewModel(),
    templatesEntity: TemplatesEntity? = null,
    onCallBack: () -> Unit,
) {
    var templatesStatusEntity:TemplatesStatusEntity? by remember { mutableStateOf(null) }
    var tempTemplatesStatusEntity:TemplatesStatusEntity? by remember { mutableStateOf(null) }
    val templateWithStatusList by viewModel.templateWithStatusList.collectAsState(null)
    val uiState by viewModel.uiState.collectAsState()

    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog) {
        AppDeleteDialog(
            onDismissRequest = { openDeleteDialog = false },
            properties = DialogProperties(),
            onDelete = {
                onCallBack()
                viewModel.deleteTemplate(templatesEntity)
                openDeleteDialog = false
            })
    }

    var openBottomSheet by remember { mutableStateOf(false) }
    if (openBottomSheet) {
        BottomSheetTemplateStatus(
            isVisible = openBottomSheet,
            templatesStatusEntity = templatesStatusEntity?:tempTemplatesStatusEntity,
            onCancelClick = {
                templatesStatusEntity = null
                tempTemplatesStatusEntity = null
                openBottomSheet = false

            },
            onDoneClick = {
                if (templatesEntity!=null){
                    // BE IN DATABASE
                    if (templatesStatusEntity!=null){
                        // UPDATE
                        viewModel.updateTemplateStatusEntity(templatesStatusEntity)
                    }
                    else{
                        // INSERT
                        viewModel.insertTemplateStatusEntity(templatesEntity)
                    }
                }
                else{
                    // NOT BE IN DATABASE
                    if (tempTemplatesStatusEntity!=null){
                        // UPDATE
                        viewModel.updateTempTemplateStatus(tempTemplatesStatusEntity!!)
                    }
                    else{
                        // INSERT
                        viewModel.insertTempTemplateStatus()
                    }
                }
                openBottomSheet = false
                templatesStatusEntity = null
                tempTemplatesStatusEntity = null
            },
            onDismissRequest = {
                templatesStatusEntity = null
                tempTemplatesStatusEntity = null
                openBottomSheet = false
            },
            onDelete = {
                if (templatesEntity!=null){
                    // BE IN DATABASE
                    viewModel.deleteTemplateStatusEntity(templatesStatusEntity)
                }
                else
                {
                    // NOT BE IN DATABASE
                    viewModel.deleteTempTemplateStatus(tempTemplatesStatusEntity)
                }

                templatesStatusEntity = null
                tempTemplatesStatusEntity = null
                openBottomSheet = false
            },
            onChangeTemplateStatusName = viewModel::updateTemplateStatusName,
            onChangeTemplateStatusColor = viewModel::updateTemplateStatusColor,
        )
    }



    val draggableItems by remember {
        derivedStateOf { uiState.tempTemplateStatusList.size }
    }
    val stateList = rememberLazyListState()
    val dragDropState =
        rememberDragDropState(
            lazyListState = stateList,
            draggableItemsNum = draggableItems,
            onMove = { fromIndex, toIndex ->
                viewModel.onMove(fromIndex,toIndex)
                //uiState.tempTemplateStatusList = uiState.tempTemplateStatusList.toMutableList().apply { add(toIndex, removeAt(fromIndex))}
            })


    Column {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().dragContainer(dragDropState),
            state = stateList,
        ) {
            item {
                var templateName: String by remember { mutableStateOf(templatesEntity?.name?:"") }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 5.dp)
                ) {
                    AppOutlineTextFieldStatic1(
                        value = templateName,
                        placeHolder = "Enter template name",
                        onValueChange = {
                            templateName = it
                            viewModel.updateTemplateName(it)
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

            if (templatesEntity != null) {
                templateWithStatusList?.let {
                    itemsIndexed(it.statuses){ index,item ->
                        StatusesItem(
                            index = index,
                            templateStatusEntity = item,
                            onMenuClick = {
                            templatesStatusEntity = it
                            openBottomSheet = true
                        })
                    }
                }
            }
            else {
                draggableItems(items = uiState.tempTemplateStatusList, dragDropState = dragDropState) { modifier, item ,index ->
                    StatusesItem(
                        modifier = modifier,
                        index = index,
                        templateStatusEntity = item,
                        onMenuClick = {
                            tempTemplatesStatusEntity = it
                            openBottomSheet = true
                        })
                }
                /*itemsIndexed(uiState.tempTemplateStatusList){ index,item ->
                    StatusesItem(
                        index = index,
                        templateStatusEntity = item,
                        onMenuClick = {
                            tempTemplatesStatusEntity = it
                            openBottomSheet = true
                        })
                }*/
            }


            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 10.dp)
                ) {
                    AppElevatedButtonWithIcon(
                        onClick = {
                            openBottomSheet = true
                        },
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
                                AppIcon(AppIcons.Add, contentDescription = null, tint = Color.Gray)
                                Spacer(Modifier.width(5.dp))
                                AppText(
                                    "Add Status",
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 3.dp)
                                )
                            }
                        }
                    )
                }
            }

            if (templatesEntity != null) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 23.dp, end = 23.dp, bottom = 5.dp, top = 5.dp)
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


            item { Spacer(Modifier.height(30.dp)) }
        }
    }
}


@Composable
private fun StatusesItem(
    modifier: Modifier = Modifier,
    index: Int,
    templateStatusEntity: TemplatesStatusEntity,
    onMenuClick: (TemplatesStatusEntity) -> Unit,
) {
    Box(
        modifier
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
            AppIcon(AppIcons.DragIndicator, contentDescription = "", tint = Color.Gray,modifier = modifier)
            Spacer(Modifier.width(10.dp))
            Box(
                Modifier
                    .size(16.dp)
                    .background(
                        color = Color.hexStringToColor(templateStatusEntity.color) ?: Color.Gray,
                        shape = RoundedCornerShape(5.dp)
                    )
            )

            Spacer(Modifier.width(10.dp))
            AppText(
                templateStatusEntity.name,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 3.dp)
            )

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                AppIcon(
                    AppIcons.MoreHoriz,
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.clickableWithNoRipple {
                        onMenuClick(templateStatusEntity)
                    })
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TemplateUpsertScreenPreview() {
    var open = remember { mutableStateOf(true) }
    TemplateUpsertScreen(onCallBack = {})
}