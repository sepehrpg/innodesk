package com.innodesk.project_management.templates_statuses.templates.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.designsystem.component.AppBottomSheetDragHandle
import com.example.designsystem.component.AppCustomSearchBarBasicTextField
import com.example.designsystem.component.AppModalBottomSheet
import com.example.designsystem.theme.ClickUpWhiteBackground2
import com.innodesk.project_management.templates_statuses.TemplatesStatusViewModel
import com.innodesk.project_management.templates_statuses.templates.screen.TemplatesScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTemplate(
    isVisible: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    viewModel: TemplatesStatusViewModel = hiltViewModel(),
    onCancelClick: () -> Unit,
    onDoneClick: (templateEntity:TemplatesEntity?) -> Unit,
    onDismissRequest: ()-> Unit,
) {

    var templateEntity:TemplatesEntity? by remember { mutableStateOf(null) }

    var search by remember { mutableStateOf("") }

    LaunchedEffect(search) {
        viewModel.updateSearchQuery(search)
    }

    AppModalBottomSheet(
        isVisible = isVisible,
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = {
            templateEntity = null
            onDismissRequest()
        },
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            AppBottomSheetDragHandle(
                onDoneClick = {
                    onDoneClick(templateEntity)
                },
                onCancelClick = {
                    templateEntity = null
                    onCancelClick()
                },
                title = "Template",
                done = "Done",
                cancel = "Cancel",
                content = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp)) {
                        Spacer(Modifier.height(15.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)) {
                            AppCustomSearchBarBasicTextField(
                                value = search,
                                onValueChange = {
                                    search = it
                                },
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        ClickUpWhiteBackground2, ClickUpWhiteBackground2
                                    )
                                ),
                                clearTextField = { search = "" },
                                shadowValue = 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                        Spacer(Modifier.height(15.dp))
                    }
                }
            )
        },
        content = {
            TemplatesScreen(itemSelected = {
                templateEntity = it
            })
        }
    )
}






@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BottomSheetTemplatePreview() {
    var open by remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            open = true
        }) {
            Text("OpenBottomSheet")
        }

        BottomSheetTemplate(
            isVisible = open,
            sheetState = rememberStandardBottomSheetState(),
            onCancelClick = {},
            onDoneClick = {},
            onDismissRequest = {}
        )
    }
}