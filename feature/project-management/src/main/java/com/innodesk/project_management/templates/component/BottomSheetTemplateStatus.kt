package com.innodesk.project_management.templates.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.database.model.pm.templates.TemplatesStatusEntity
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppModalBottomSheet
import com.innodesk.project_management.templates.TemplateStatusScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTemplateStatus(
    isVisible: Boolean,
    templatesStatusEntity:TemplatesStatusEntity? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    onDismissRequest: ()-> Unit,
    onChangeTemplateStatusName : (String) -> Unit,
    onChangeTemplateStatusColor : (Color) -> Unit,
    onDelete : (TemplatesStatusEntity) -> Unit,
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
                title = if(templatesStatusEntity!=null)  "Edit Status" else "Add Status",
                done = "Done",
                cancel = "Cancel",
            )
        },
        content = {
            TemplateStatusScreen(
                onDelete = onDelete,
                templatesStatusEntity = templatesStatusEntity,
                onChangeTemplateStatusName = onChangeTemplateStatusName,
                onChangeTemplateStatusColor = onChangeTemplateStatusColor
            )
        }
    )
}






@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetTemplateStatusPreview(){
    var open by remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            open = true
        }) {
            Text("OpenBottomSheet")
        }
        BottomSheetTemplateStatus(
            isVisible = open,
            /*sheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Expanded,
                skipHiddenState = false
            ),*/
            onCancelClick = {}, onDoneClick = {},
            onDismissRequest = {},
            onDelete = {},
            onChangeTemplateStatusName = {},
            onChangeTemplateStatusColor = {},
        )
    }
}