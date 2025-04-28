package com.innodesk.project_management.templates_statuses.templates.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppIconButton
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.templates_statuses.TemplatesStatusViewModel
import com.innodesk.project_management.templates_statuses.templates.component.BottomSheetTemplateUpsert


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesScreen(
    viewModel: TemplatesStatusViewModel = hiltViewModel(),
    itemSelected: (TemplatesEntity) -> Unit
) {
    val defTemplateList by viewModel.templateList.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val templateList = if (searchQuery.isBlank()) {
        defTemplateList
    } else {
        defTemplateList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    var templatesEntity: TemplatesEntity? by remember { mutableStateOf<TemplatesEntity?>(null) }
    var openBottomSheet by remember { mutableStateOf(false) }

    if (openBottomSheet) {
        BottomSheetTemplateUpsert(
            isVisible = openBottomSheet,
            templatesEntity = templatesEntity,
            onCancelClick = {
                templatesEntity = null
                viewModel.clearUpsertTemplate()
                openBottomSheet = false
            },
            onDeleteTemplate = {
                viewModel.deleteTemplate(templatesEntity)
                viewModel.clearUpsertTemplate()
                templatesEntity = null
                openBottomSheet = false
            },
            onDoneClick = {
                val validationPass = if (templatesEntity != null) {
                    // In Database
                    viewModel.updateTemplatesWithStatusList(templatesEntity)
                } else {
                    //Not In Database
                    viewModel.insertTemplateWithStatuses()
                }

                if (validationPass) {
                    templatesEntity = null
                    openBottomSheet = false
                    viewModel.clearUpsertTemplate()
                }
            },
            onDismissRequest = {
                templatesEntity = null
                viewModel.clearUpsertTemplate()
                openBottomSheet = false
            }
        )
    }


    TemplatesScreen(
        templateList = templateList,
        onClickAddItem = {
            viewModel.clearUpsertTemplate()
            openBottomSheet = true
        },
        templatesEntity = templatesEntity,
        onItemSelectedTemplateItem = {
            templatesEntity = it
            itemSelected(it)
        },
        onMenuClickTemplateItem = { templatesEntityClicked, itemSelectedClicked ->
            templatesEntity = templatesEntityClicked
            openBottomSheet = true
            viewModel.isDataLoaded = false
            viewModel.getTemplateWithStatus(itemSelectedClicked.id)
        }
    )

}


@Composable
private fun TemplatesScreen(
    templateList: List<TemplatesEntity>,
    templatesEntity: TemplatesEntity?,
    onClickAddItem: () -> Unit,
    onItemSelectedTemplateItem: (TemplatesEntity) -> Unit,
    onMenuClickTemplateItem: (TemplatesEntity?, itemSelected: TemplatesEntity) -> Unit,
) {

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
                            AppIconButton(onClick = onClickAddItem) {
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
            itemsIndexed(templateList) { index, item ->
                TemplateItem(
                    templatesEntity = item,
                    selected = templatesEntity == item,
                    onItemSelected = onItemSelectedTemplateItem,
                    onMenuClick = {
                        onMenuClickTemplateItem(templatesEntity, item)
                    }
                )
            }
            item { Spacer(Modifier.height(40.dp)) }

        }
    }

}


@Composable
private fun TemplateItem(
    templatesEntity: TemplatesEntity,
    selected: Boolean,
    onItemSelected: (TemplatesEntity) -> Unit,
    onMenuClick: (TemplatesEntity) -> Unit
) {

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier
            .fillMaxWidth()
            .clickableWithNoRipple {
                onItemSelected(templatesEntity)
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            Box(
                Modifier.sizeIn(minWidth = 25.dp),
                contentAlignment = Alignment.Center
            ) {
                if (selected) {
                    AppIcon(AppIcons.Done, contentDescription = "", tint = PrimaryColor)
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(4f)
            ) {
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
                            AppText(templatesEntity.name, fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(Modifier.width(5.dp))
                        Box(Modifier.clickableWithNoRipple {
                            onMenuClick(templatesEntity)
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TemplatesScreenPreview() {
    TemplatesScreen(
        templateList = emptyList(),
        templatesEntity = null,
        onClickAddItem = {},
        onItemSelectedTemplateItem = {},
        onMenuClickTemplateItem = { templatesEntity, itemSelected ->
        },
    )
}