package com.innodesk.project_management.projects.screen

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
import androidx.compose.foundation.lazy.items
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
import com.example.database.model.pm.project.ProjectsEntity
import com.example.designsystem.component.AppExtendedFloatingActionButton
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.projects.ProjectsViewModel
import com.innodesk.project_management.projects.component.BottomSheetsProjectUpsert


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    viewModel: ProjectsViewModel = hiltViewModel(),
    onNavigateToWorkSpaceScreen: (projectID:Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val projects by viewModel.projectsList.collectAsState(initial = emptyList())
    var isVisibleBottomSheet by remember { mutableStateOf(false) }


    var selectedProject by remember { mutableStateOf<ProjectsEntity?>(null) }

    ProjectsScreenContent(
        projects = projects,
        openBottomSheet = {
            isVisibleBottomSheet = true
            viewModel.clearData()
        },
        onClickProjectEdit = {
            selectedProject = it
            isVisibleBottomSheet = true
        },
        onNavigateToWorkSpaceScreen = onNavigateToWorkSpaceScreen
    )

    BottomSheetsProjectUpsert(
        isVisible = isVisibleBottomSheet,
        onDismissRequest = {
            selectedProject = null
            isVisibleBottomSheet = false
        },
        onDoneClick = {
            val validationPassed = if (selectedProject != null) {
                viewModel.updateProjectEntity(selectedProject!!)
            } else {
                viewModel.insertProjectEntity()
            }
            if (validationPassed) {
                selectedProject = null
                isVisibleBottomSheet = false
            }
        },
        projectsEntity = selectedProject,
        onDeleteProject = {
            viewModel.deleteProjectEntity(it)
            isVisibleBottomSheet = false
            selectedProject = null
        },
    )

}



@Composable
fun ProjectsScreenContent(
    projects: List<ProjectsEntity>,
    openBottomSheet: () -> Unit,
    onClickProjectEdit: (ProjectsEntity) -> Unit,
    onNavigateToWorkSpaceScreen: (projectID:Int) -> Unit,
) {

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 10.dp), contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(projects) {
                ProjectItem(it, onClickProjectEdit = onClickProjectEdit,onNavigateToWorkSpaceScreen = onNavigateToWorkSpaceScreen)
            }

            item { Spacer(Modifier.height(70.dp)) }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 10.dp, start = 10.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AppExtendedFloatingActionButton(
                onClick = openBottomSheet,
                containerColor = PrimaryColor,
                icon = { AppIcon(AppIcons.PostAdd, contentDescription = "") },
                text = {
                    AppText(
                        "New Project",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            )
        }
    }

}

@Composable
private fun ProjectItem(
    item: ProjectsEntity,
    onClickProjectEdit: (ProjectsEntity) -> Unit,
    onNavigateToWorkSpaceScreen: (projectID:Int) -> Unit,
) {
    Column(Modifier.fillMaxWidth().clickableWithNoRipple {
        onNavigateToWorkSpaceScreen(item.id?:0)
    }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 15.dp),
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
                    AppText(item.name, color = Color.Black, fontSize = 14.sp)
                }
                Spacer(Modifier.width(5.dp))
                AppText("1", fontSize = 14.sp)
                Spacer(Modifier.width(5.dp))
                Box(Modifier.clickableWithNoRipple {
                    onClickProjectEdit(item)
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
        HorizontalDivider(thickness = 4.dp, color = Color(0xFFEEEEEE))
    }
}


@Preview(showBackground = true)
@Composable
fun ProjectScreenPreview() {
    ProjectsScreen(onNavigateToWorkSpaceScreen = {})
}