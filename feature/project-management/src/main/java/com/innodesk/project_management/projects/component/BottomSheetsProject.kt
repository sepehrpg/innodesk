package com.innodesk.project_management.projects.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.database.model.pm.project.ProjectAccess
import com.example.database.model.pm.project.ProjectsEntity
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.component.AppCustomLeadingIconTab
import com.example.designsystem.component.AppCustomLeadingIconTabItem
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.component.AppText
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppColorPickerLibrary1
import com.example.designsystem.component.AppDeleteDialog
import com.example.designsystem.component.AppElevatedButtonWithIcon
import com.example.designsystem.component.AppFilledIconButton
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppOutlineTextFieldStatic1
import com.example.designsystem.extension.hexStringToColor
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.projects.ProjectUpsertScreen
import com.innodesk.project_management.templates.component.BottomSheetTemplate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetsProject(
    isVisible: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    projectsEntity: ProjectsEntity? = null,
    onValueChangeProjectName: (String) -> Unit,
    onValueChangeColorValue: (Color) -> Unit,
    onDeleteProject: (ProjectsEntity?) -> Unit,
    onValueChangeProjectAccess: (ProjectAccess) -> Unit,
    onDismissRequest: ()-> Unit,
    onDoneClick: () -> Unit
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
                onValueChangeProjectName = onValueChangeProjectName,
                onValueChangeColorValue = onValueChangeColorValue,
                onValueChangeProjectAccess = onValueChangeProjectAccess,
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
        BottomSheetsProject(
            isVisible = open,
            onDismissRequest = {open=false},
            sheetState = rememberStandardBottomSheetState(),
            onValueChangeProjectName = {},
            onValueChangeColorValue = {},
            onDeleteProject = {},
            onValueChangeProjectAccess = {},
            onDoneClick = {},
        )
    }
}
