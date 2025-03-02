package com.innodesk.demo.database

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.example.designsystem.component.AppElevatedButton
import com.example.designsystem.component.AppFloatingActionButton
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppIconButton
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.component.AppText
import com.example.designsystem.icon.AppIcons
import com.innodesk.project_management.utils.TypeScreen


@Composable
fun DatabaseScreen(
    viewModel: DatabaseViewModel = hiltViewModel(),
    mockProjects: List<ProjectsEntity> = emptyList()
) {

    val projects by if (mockProjects.isNotEmpty()) {
        remember { mutableStateOf(mockProjects) }
    } else {
        viewModel.projectsFlow.collectAsState(emptyList())
    }

    ContentScreen(
        projects = projects,
        insertProject = {
            viewModel.insertProject(it!!)
        },
        updateProject = {
            viewModel.updateProject(it!!)
        },
        deleteProject = {
            viewModel.deleteProject(it!!)
        },

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    projects: List<ProjectsEntity>,
    insertProject: (project:ProjectsEntity?) -> Unit,
    updateProject: (project:ProjectsEntity?) -> Unit,
    deleteProject: (project:ProjectsEntity?) -> Unit,
) {
    var isVisibility by remember { mutableStateOf(false) }
    var project:ProjectsEntity? by remember { mutableStateOf(null) }
    var typeScreen by remember { mutableStateOf(TypeScreen.DEFAULT) }


    AppModalBottomSheet(isVisible = isVisibility, onDismissRequest = { isVisibility=false}) {

        var value by remember { mutableStateOf(if (typeScreen==TypeScreen.EDIT) project?.name?:"" else "") }

        Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp)){
            AppOutlineTextFieldStatic1(value=value, onValueChange = {
                value = it
            })

            Spacer(Modifier.height(10.dp))
            AppElevatedButton(modifier = Modifier.fillMaxWidth(),onClick = {
                if(typeScreen==TypeScreen.EDIT){
                    project = project?.copy(name = value)
                    updateProject(project)
                } else{
                    insertProject(ProjectsEntity(
                        name = value,
                    ))
                }
                isVisibility = false
            }) {
                AppText("Done")
            }
        }
    }

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = {
                    isVisibility = true
                    typeScreen = TypeScreen.ADD
                },
                content = { AppIcon(Icons.Default.Add, contentDescription = null) }
            )
        }
    ) {
        val padding = it
        Box(Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(projects) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        AppText(
                            it.name,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        AppIconButton(onClick = {
                            deleteProject(it)
                        }) {
                            Icon(AppIcons.Delete, contentDescription = "Delete")
                        }
                        AppIconButton(onClick = {
                            isVisibility = true
                            typeScreen = TypeScreen.EDIT
                            project = it
                        }) {
                            Icon(AppIcons.Edit, contentDescription = "Edit")
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DatabaseScreenPreview() {
    ContentScreen(
        projects = listOf(
            ProjectsEntity(name = "Project 1"),
            ProjectsEntity(name = "Project 2"),
            ProjectsEntity(name = "Project 3"),
            ProjectsEntity(name = "Project 4"),
            ProjectsEntity(name = "Project 5"),
        ),
        updateProject = {},
        deleteProject = {},
        insertProject = {}
    )
}