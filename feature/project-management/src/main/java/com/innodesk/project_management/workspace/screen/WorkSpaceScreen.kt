package com.innodesk.project_management.workspace.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import com.example.designsystem.component.AppCardPrimary
import com.example.designsystem.component.AppContainerPrimary
import com.example.designsystem.component.AppFloatingActionButton
import com.example.designsystem.component.AppHorizontalPagerPrimary
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppIconButton
import com.example.designsystem.component.AppPagerIndicatorPrimary
import com.example.designsystem.component.AppText
import com.example.designsystem.component.dragContainer
import com.example.designsystem.component.draggableItems
import com.example.designsystem.component.rememberDragDropState
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.extension.hexStringToColor
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray3
import com.example.designsystem.theme.ClickUpGray5
import com.example.designsystem.theme.PrimaryColor
import com.innodesk.project_management.workspace.WorkSpaceUiState
import com.innodesk.project_management.workspace.WorkSpaceViewModel
import com.innodesk.project_management.workspace.component.BottomSheetWorkSpaceMenu
import com.innodesk.project_management.workspace.component.BottomSheetWorkSpaceStatusMenu
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkSpaceScreen(
    onBackClick: () -> Unit,
    viewModel: WorkSpaceViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = { uiState.templateStatusesList.size + 1 })

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Timber.d("Page change", "Page changed to $page")
        }
    }

    var templateStatus: TemplatesStatusEntity? by remember { mutableStateOf(null) }
    var isOpenBottomSheetStatusMenu by remember { mutableStateOf(false) }
    var isOpenBottomSheetMenu by remember { mutableStateOf(false) }
    WorkSpaceScreen(
        uiState,
        pagerState,
        openBottomSheetStatusMenu = { template ->
        templateStatus = template
        template?.name?.let {
            viewModel.updateTemplateStatusName(it)
        }
        template?.color?.let {
            Color.hexStringToColor(it)?.let { color ->
                viewModel.updateTemplateStatusColor(color)
            }
        }
        isOpenBottomSheetStatusMenu = true
    },
        openBottomSheetMenu = {
            isOpenBottomSheetMenu=true
        },
        onBackClick = onBackClick,

    )

    if (isOpenBottomSheetStatusMenu) {
        BottomSheetWorkSpaceStatusMenu(
            isVisible = isOpenBottomSheetStatusMenu,
            templatesStatusEntity = templateStatus,
            onCancelClick = {
                isOpenBottomSheetStatusMenu = false
            },
            onDoneClick = {
                val validationPass = if (templateStatus != null) {
                    viewModel.databaseUpdateTemplateStatus(templateStatus)
                } else {
                    viewModel.databaseInsertTemplateStatus()
                }

                if (validationPass) {
                    isOpenBottomSheetStatusMenu = false
                }
            },
            onDismissRequest = {
                isOpenBottomSheetStatusMenu = false
            },
            onChangeTemplateStatusName = {
                viewModel.updateTemplateStatusName(it)
            },
            onChangeTemplateStatusColor = {
                viewModel.updateTemplateStatusColor(it)
            },
            onDelete = {
                viewModel.databaseDeleteTemplateStatus(it)
                isOpenBottomSheetStatusMenu = false
            }
        )
    } else {
        templateStatus = null
        viewModel.clearData()
    }

    if (isOpenBottomSheetMenu) {
        BottomSheetWorkSpaceMenu (
            isVisible = isOpenBottomSheetMenu,
            templatesStatusEntity = templateStatus,
            onCancelClick = {
                isOpenBottomSheetMenu = false
            },
            onDoneClick = {
                val validationPass = if (templateStatus != null) {
                    viewModel.databaseUpdateTemplateStatus(templateStatus)
                } else {
                    viewModel.databaseInsertTemplateStatus()
                }

                if (validationPass) {
                    isOpenBottomSheetMenu = false
                }
            },
            onDismissRequest = {
                isOpenBottomSheetMenu = false
            },
            onChangeTemplateStatusName = {
                viewModel.updateTemplateStatusName(it)
            },
            onChangeTemplateStatusColor = {
                viewModel.updateTemplateStatusColor(it)
            },
            onDelete = {
                viewModel.databaseDeleteTemplateStatus(it)
                isOpenBottomSheetMenu = false
            }
        )
    } else {
        templateStatus = null
        viewModel.clearData()
    }


}


@Composable
private fun WorkSpaceScreen(
    uiState: WorkSpaceUiState?,
    pagerState: PagerState,
    openBottomSheetStatusMenu: (templateStatuses: TemplatesStatusEntity?) -> Unit,
    openBottomSheetMenu: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = ClickUpGray3,
        topBar = { TopBar(uiState, onBackClick = onBackClick,openBottomSheetMenu=openBottomSheetMenu) },
        floatingActionButton = {
            AppFloatingActionButton(
                containerColor = PrimaryColor,
                onClick = {},
                shape = CircleShape,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(AppIcons.Add, contentDescription = "", tint = Color.White)
            }
        }
    ) {
        val paddingValues = it
        Box(modifier = Modifier.padding(paddingValues)) {
            if (!uiState?.templateStatusesList.isNullOrEmpty()) {
                AppHorizontalPagerPrimary(
                    state = pagerState,
                    //userScrollEnabled = false,
                    modifier = Modifier.fillMaxSize(),
                    beyondViewportPageCount = 10,
                    contentPadding = PaddingValues(
                        end = if (pagerState.pageCount == 1) 0.dp else 50.dp
                    ),
                    //end = if(pagerState.pageCount==1 || pagerState.currentPage==pagerState.pageCount-1) 0.dp else 50.dp),
                    pageSpacing = 5.dp,
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        TaskInStatusComponent(uiState, page, openBottomSheetStatusMenu)
                    }
                }
                if (pagerState.pageCount > 1) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AppPagerIndicatorPrimary(pagerState)
                    }
                }
            }
        }
    }
}


@Composable
private fun TopBar(
    uiState: WorkSpaceUiState?,
    openBottomSheetMenu: () -> Unit,
    onBackClick: () -> Unit,
) {
    //
    Box(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .shadow(3.dp, shape = RectangleShape, clip = false)
            .background(Color.White),
    ) {
        Column(Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                AppIconButton(modifier = Modifier.weight(1f), onClick = onBackClick) {
                    AppIcon(
                        AppIcons.ArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.size(27.dp),
                        tint = Color.Gray
                    )
                }
                Spacer(Modifier.width(17.dp))

                Row(Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically) {
                    AppContainerPrimary(
                        modifier = Modifier
                            .size(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = ClickUpGray3
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            AppIcon(AppIcons.DonutLarge, contentDescription = "", tint = Color.Gray)
                        }
                    }
                    Spacer(Modifier.width(5.dp))
                    AppText(
                        uiState?.projectEntity?.name ?: "Project",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                }
                Row(Modifier
                    .fillMaxWidth()
                    .weight(1f), horizontalArrangement = Arrangement.End) {

                    if (!uiState?.templateStatusesList?.filter { it.templateId==0 }.isNullOrEmpty()){
                        AppIcon(AppIcons.Unarchive, contentDescription = "", tint = Color.Gray)
                    }
                    /*AppIcon(AppIcons.Share, contentDescription = "", tint = Color.Gray)
                    Spacer(Modifier.width(15.dp))
                    */
                    Spacer(Modifier.width(15.dp))
                    AppIcon(AppIcons.MoreHoriz, contentDescription = "", tint = Color.Gray,
                        modifier = Modifier.clickableWithNoRipple {
                            openBottomSheetMenu()
                        })
                    Spacer(Modifier.width(15.dp))
                }

            }
        }
    }
}


@Composable
private fun TaskInStatusComponent(
    uiState: WorkSpaceUiState?,
    page: Int,
    openBottomSheet: (templateStatuses: TemplatesStatusEntity?) -> Unit
) {

    uiState?.let {
        if (page == uiState.templateStatusesList.size) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppCardPrimary(
                    Modifier
                        .fillMaxWidth()
                        .clickableWithNoRipple {
                            openBottomSheet(null)
                        },
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(Color.Transparent),
                    border = BorderStroke(1.dp, color = ClickUpGray5)
                    //colors = CardDefaults.cardColors(containerColor = ClickUpGray5)
                ) {
                    Box(
                        Modifier
                            .padding(vertical = 0.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppIcon(AppIcons.Add, contentDescription = "", tint = ClickUpGray5)
                            Spacer(Modifier.width(2.dp))
                            AppText(
                                "ADD STATUS",
                                color = ClickUpGray5,
                                modifier = Modifier.padding(top = 4.dp),
                                fontSize = 13.sp, fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            val templateStatuses = uiState.templateStatusesList[page]
            Column(
                Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
            ) {
                StatusHeader(uiState, templateStatuses, openBottomSheet)
                TaskListComponent(uiState, templateStatuses)
            }
        }
    }


}


@Composable
private fun StatusHeader(
    uiState: WorkSpaceUiState,
    templateStatuses: TemplatesStatusEntity?,
    openBottomSheet: (templateStatuses: TemplatesStatusEntity?) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppCardPrimary(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.hexStringToColor(
                    templateStatuses?.color
                ) ?: ClickUpGray5
            )
            //colors = CardDefaults.cardColors(containerColor = ClickUpGray5)
        ) {
            Box(
                Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    templateStatuses?.name ?: "",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.width(10.dp))
        AppIcon(AppIcons.Add, contentDescription = "", tint = ClickUpGray5)
        Spacer(Modifier.width(10.dp))
        AppIcon(
            AppIcons.MoreHoriz,
            contentDescription = "",
            tint = ClickUpGray5,
            modifier = Modifier.clickableWithNoRipple {
                openBottomSheet(templateStatuses)
            })
    }
}


@Composable
private fun TaskListComponent(uiState: WorkSpaceUiState, templateStatuses: TemplatesStatusEntity?) {

    var list1 = uiState.tasksList.filter { it.templateStatusId == templateStatuses?.id }

    val draggableItems by remember {
        derivedStateOf { list1.size }
    }
    val stateList = rememberLazyListState()
    val dragDropState =
        rememberDragDropState(
            lazyListState = stateList,
            draggableItemsNum = draggableItems,
            onMove = { fromIndex, toIndex ->
                list1 = list1.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
            })

    LazyColumn(
        Modifier
            .padding(top = 15.dp)
            .dragContainer(dragDropState),
        state = stateList,
    ) {
        draggableItems(items = list1, dragDropState = dragDropState) { modifier, item, index ->
            TaskItem(modifier, index, uiState, templateStatuses)
        }
    }
}


@Composable
private fun TaskItem(
    modifier: Modifier,
    index: Int,
    uiState: WorkSpaceUiState?,
    templateStatuses: TemplatesStatusEntity?
) {
    AppCardPrimary(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .heightIn(min = 60.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.hexStringToColor(templateStatuses?.color) ?: ClickUpGray5)
            )
            Spacer(Modifier.width(10.dp))
            AppText("Task $index", color = Color.DarkGray, modifier = Modifier.weight(1f))
            AppIcon(AppIcons.DragIndicator, contentDescription = "", tint = Color.Gray)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkSpaceScreenPreview() {
    val pagerState = rememberPagerState(pageCount = { 0 })

    //TaskListComponent()
    WorkSpaceScreen(null, pagerState, {}, onBackClick = {},openBottomSheetMenu={})
}