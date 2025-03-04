package com.innodesk.project_management.projects.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.database.model.pm.project.ProjectAccess
import com.example.database.model.pm.project.ProjectsEntity
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.innodesk.project_management.projects.ProjectUpsertScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetsProjectUpsert(
    isVisible: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: ()-> Unit,
    onDoneClick: () -> Unit,
    projectsEntity: ProjectsEntity? = null,
    onDeleteProject: (ProjectsEntity?) -> Unit,
) {

    AppModalBottomSheet(
        modifier = Modifier.statusBarsPadding(),
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            AppBottomSheetDragHandle(
                onDoneClick = onDoneClick,
                onCancelClick = onDismissRequest,
                title = if (projectsEntity!=null) "Edit Project" else "Create Project",
                done = if (projectsEntity!=null) "Edit" else "Create",
                cancel = "Cancel"
            )
        },
        content = {
            ProjectUpsertScreen(
                projectsEntity = projectsEntity,
                onDeleteProject = onDeleteProject
            )
        }
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetsProjectPreview() {
    var open by remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            open = true
        }) {
            Text("OpenBottomSheet")
        }
        BottomSheetsProjectUpsert(
            isVisible = open,
            onDismissRequest = {open=false},
            sheetState = rememberStandardBottomSheetState(),
            onDeleteProject = {},
            onDoneClick = {},
        )
    }
}
