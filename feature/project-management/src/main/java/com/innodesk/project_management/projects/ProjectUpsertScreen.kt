package com.innodesk.project_management.projects

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.model.pm.project.ProjectAccess
import com.example.database.model.pm.project.ProjectsEntity
import com.example.designsystem.component.AppColorPickerLibrary1
import com.example.designsystem.component.AppCustomLeadingIconTab
import com.example.designsystem.component.AppCustomLeadingIconTabItem
import com.example.designsystem.component.AppDeleteDialog
import com.example.designsystem.component.AppElevatedButtonWithIcon
import com.example.designsystem.component.AppFilledIconButton
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.component.AppText
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.extension.hexStringToColor
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.templates.TemplateUpsertScreen
import com.innodesk.project_management.templates.component.BottomSheetTemplate
import com.innodesk.project_management.utils.TypeScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectUpsertScreen(
    viewModel: ProjectsViewModel = hiltViewModel(),
    projectsEntity: ProjectsEntity?,
    onDeleteProject: (ProjectsEntity?) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(projectsEntity){
        projectsEntity?.let {
            viewModel.updateProjectName(it.name)
            viewModel.updateProjectAccessId(it.projectAccess)
            viewModel.updateProjectColor(Color.hexStringToColor(it.color))
        }
    }

    var projectName: String by remember { mutableStateOf(projectsEntity?.name ?: "") }
    var tempSelected by remember {
        mutableStateOf(
            if (projectsEntity?.projectAccess != null) projectsEntity.projectAccess else ProjectAccess.PRIVATE
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    var colorValue: Color? by remember {
        mutableStateOf(
            if (projectsEntity?.color != null) Color.hexStringToColor(projectsEntity.color!!) else null
        )
    }


    var openDeleteDialog by remember { mutableStateOf(false) }
    if (openDeleteDialog) {
        AppDeleteDialog(
            onDismissRequest = { openDeleteDialog = false },
            properties = DialogProperties(),
            onDelete = {
                onDeleteProject(projectsEntity)
            })
    }


    AppColorPickerLibrary1(
        showDialog,
        onDismissRequest = { showDialog = false },
        colorValue = {
            colorValue = it
            viewModel.updateProjectColor(it)
        }
    )

    var openBottomSheet by remember { mutableStateOf(false) }
    if (openBottomSheet) {
        BottomSheetTemplate(
            isVisible = openBottomSheet,
            onCancelClick = {
                openBottomSheet = false
            },
            onDoneClick = {
                openBottomSheet = false
            },
            onDismissRequest = {
                openBottomSheet = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {

        item {
            Column(Modifier.padding(top = 10.dp)) {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    AppText("Project Name", fontSize = 14.sp, color = Color.DarkGray)
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                ) {
                    AppOutlineTextFieldStatic1(
                        value = projectName,
                        placeHolder = "Enter project name",
                        onValueChange = {
                            projectName = it
                            viewModel.updateProjectName(projectName)
                        }
                    )
                }
            }
        }


        item {

            val context = LocalContext.current

            Column(Modifier.padding(top = 20.dp)) {
                Box(Modifier.padding(horizontal = 5.dp)) {
                    AppText("Access", fontSize = 14.sp, color = Color.DarkGray)
                }

                val list: List<AppCustomLeadingIconTabItem> = ProjectAccess.entries.map {
                    AppCustomLeadingIconTabItem(
                        id = it.id,
                        description = it.descriptions(context),
                        additionalUi = it == ProjectAccess.PRIVATE,
                        selected = tempSelected == it,
                        text = {
                            Text(
                                it.name(context),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            AppIcon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Group,
                                contentDescription = "Home Icon"
                            )
                        }
                    )
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                ) {
                    AppCustomLeadingIconTab(
                        item = list,
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            //list.map { it.nonSelected() }
                            //list[it].isSelected()
                            tempSelected = ProjectAccess.entries[it]
                            viewModel.updateProjectAccessId(ProjectAccess.entries[it])
                        },
                        sharedClick = {
                            coroutineScope.launch {
                                SnackBarManager.showSnackBar("This Feature Adding Soon")
                            }
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

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ClickUpGray2)
                ) {

                    if (projectsEntity == null) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickableWithNoRipple {
                                    openBottomSheet = true
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
                                AppText("Templates", fontSize = 13.sp)
                            }
                            AppIcon(
                                if(uiState.templateId!=null) AppIcons.Done else AppIcons.ArrowRight,
                                contentDescription = "",
                                tint = if(uiState.templateId!=null) Color(0xFF003919) else Color.Gray
                            )
                        }
                        HorizontalDivider(color = Color((0xFFEEEEEE)))
                    }


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickableWithNoRipple {
                                showDialog = true
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
                            AppText("Color", fontSize = 13.sp)
                        }

                        if (colorValue != null) {
                            Box(
                                Modifier
                                    .size(25.dp)
                                    .background(
                                        color = colorValue!!,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            )
                        } else {
                            AppIcon(AppIcons.Add, contentDescription = "", tint = Color.Gray)
                        }
                    }
                }


                /*repeat(3) {
                    ProjectTemplateItem(if (it == 1) true else false)
                }*/

            }
        }

        if (projectsEntity != null) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 5.dp)
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
                    AppIcon(AppIcons.MoreHoriz, contentDescription = "MoreHoriz", tint = Color.Gray)
                }
            }
        }
        HorizontalDivider(thickness = 0.1.dp, color = Color(0xFFEEEEEE))
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProjectUpsertScreenPreview() {
    var open = remember { mutableStateOf(true) }
    ProjectUpsertScreen(
        projectsEntity = null,
        onDeleteProject = { },
    )
}