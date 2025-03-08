package com.innodesk.project_management

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.rounded.UnfoldMore
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.extension.adjustWarmth
import com.example.designsystem.component.AppCustomSearchBarBasicTextField
import com.example.designsystem.component.AppFilledTonalIconButton
import com.example.designsystem.component.AppGradientBackground
import com.example.designsystem.component.AppIcon
import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.AppText
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.theme.AppTheme
import com.example.designsystem.theme.GradientColor1
import com.example.designsystem.theme.GradientColor2
import com.example.designsystem.theme.GradientColor3
import com.example.designsystem.theme.GradientColor4
import com.example.designsystem.theme.GradientColor5
import com.example.designsystem.theme.GradientColor6
import com.example.designsystem.theme.GradientColor7
import com.example.designsystem.theme.GradientColors
import com.innodesk.project_management.projects.ProjectUpsertScreen
import com.innodesk.project_management.projects.ProjectsScreen
import com.innodesk.project_management.templates.TemplateStatusScreen
import com.innodesk.project_management.templates.TemplatesScreen
import com.innodesk.project_management.templates.TemplateUpsertScreen
import kotlinx.coroutines.launch


@Composable
fun ProjectManagementMainScreen() {
    var gradientBackgroundColor by remember {
        mutableStateOf(GradientColor1)
    }
    val isPreview = LocalInspectionMode.current //Check Preview Mode

    AppTheme {
        AppGradientBackground(
            gradientColors = gradientBackgroundColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .then(
                        if (isPreview) Modifier else Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding()
                    )
            ) {
                Column(Modifier.fillMaxSize()) {
                    Column(Modifier.weight(1f)) {
                        Header()
                        Body(changeBackgroundColor = {
                            it?.let { gradientBackgroundColor = it }
                        })
                    }

                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                        Image(
                            painterResource(R.drawable.bottombar),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }


            }
        }
    }
}


@Composable
private fun Header() {
    var search by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 5.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f)) {
                AppIcon(
                    painterResource(com.example.designsystem.R.drawable.core_designsystem_user_add),
                    modifier = Modifier.size(22.dp),
                    contentDescription = ""
                )
            }
            Box(Modifier.weight(2f), contentAlignment = Alignment.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Sepehrpg", color = Color.Black, fontWeight = FontWeight.Bold)
                    AppIcon(
                        Icons.Rounded.UnfoldMore,
                        contentDescription = "",
                        Modifier.size(15.dp)
                    )
                }
            }
            Box(Modifier.weight(1f)) {
            }
        }
    }


    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 7.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) {
                AppCustomSearchBarBasicTextField(
                    value = search,
                    onValueChange = { search = it },
                    shape = RoundedCornerShape(15.dp),
                    height = 45.dp,
                    brush = Brush.horizontalGradient(listOf(Color(0xFFEEEEEE), Color.White))
                )
            }
            Spacer(Modifier.width(12.dp))
            Box {
                AppFilledTonalIconButton(
                    onClick = { /* Handle click */ },
                    modifier = Modifier.size(45.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.DarkGray
                    ),
                    boxModifier = Modifier
                        .shadow(3.dp, RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.White,
                                    Color(0xFFEEEEEE)
                                )
                            ), RoundedCornerShape(12.dp)
                        ),
                ) {
                    AppIcon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.DarkGray
                    )
                }
            }

        }
    }
}


@Composable
private fun Body(
    changeBackgroundColor: (gradientBackgroundColor: GradientColors?) -> Unit
) {
    val context = LocalContext.current
    var pageIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Box(Modifier.padding(top = 2.dp)) {
        val mod: Modifier = Modifier
            .shadow(2.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .clip(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .background(Color.White)
        AppTabPager(
            tabs = tabItem(context, pageIndex),
            scrollableTab = true,
            pagerModifier = mod,
            tabContainerColor = Color.Transparent,
            tabContentColor = Color.Black,
            tabTextColor = Color.Gray,
            tabTextColorSelected = Color.Black,
            indicatorColor = Color.DarkGray,
            indicatorWidth = 25.dp,
            indicatorShape = RoundedCornerShape(10.dp),
            thicknessIndicator = 2.dp,
            dividerColor = Color.Transparent,
            dividerThickness = 0.dp,
            pageIndexCallBack = {
                changeBackgroundColor(tabItem(context, pageIndex)[it].gradientColorsList)
                pageIndex = it
            }
        )
    }
}


private fun tabItem(context: Context, pageIndex: Int): List<AppTabPagerItems> = buildList {
    repeat(6) { index ->
        add(
            AppTabPagerItems(
                title = when (index) {
                    0 -> context.getString(R.string.dashboard)
                    1 -> context.getString(R.string.recent)
                    2 -> context.getString(R.string.projects)
                    3 -> context.getString(R.string.tasks)
                    4 -> context.getString(R.string.reminder)
                    5 -> context.getString(R.string.docs)
                    else -> ""
                },
                indicatorColor = when (index) {
                    0 -> GradientColor1.bottom
                    1 -> GradientColor2.bottom.adjustWarmth(50)
                    2 -> GradientColor3.bottom.adjustWarmth(50)
                    3 -> GradientColor4.bottom.adjustWarmth(50)
                    4 -> GradientColor5.bottom.adjustWarmth(50)
                    5 -> GradientColor6.bottom.adjustWarmth(50)
                    6 -> GradientColor7.bottom.adjustWarmth(50)
                    else -> GradientColor7.bottom.adjustWarmth(50)
                },
                icon = {
                    AppIcon(
                        imageVector = when (index) {
                            0 -> Icons.Filled.Work
                            1 -> Icons.Filled.Settings
                            2 -> Icons.Filled.Info
                            3 -> Icons.Filled.Star
                            4 -> Icons.Filled.Search
                            5 -> Icons.Filled.EmojiEvents
                            6 -> Icons.Filled.Call
                            else -> Icons.Filled.Call
                        },
                        contentDescription = when (index) {
                            0 -> "dashboard"
                            1 -> "recent"
                            2 -> "projects"
                            3 -> "tasks"
                            4 -> "reminder"
                            5 -> "docs"
                            6 -> "Call"
                            else -> "Call"
                        },
                        modifier = Modifier.size(18.dp),
                        tint = if (pageIndex == index) {
                            when (index) {
                                0 -> GradientColor1.bottom
                                1 -> GradientColor2.bottom.adjustWarmth(50)
                                2 -> GradientColor3.bottom.adjustWarmth(50)
                                3 -> GradientColor4.bottom.adjustWarmth(50)
                                4 -> GradientColor5.bottom.adjustWarmth(50)
                                5 -> GradientColor6.bottom.adjustWarmth(50)
                                6 -> GradientColor7.bottom.adjustWarmth(50)
                                else -> GradientColor7.bottom.adjustWarmth(50)
                            }
                        } else {
                            Color.Gray
                        }
                    )
                },
                gradientColorsList = when (index) {
                    0 -> GradientColor1
                    1 -> GradientColor2
                    2 -> GradientColor3
                    3 -> GradientColor4
                    4 -> GradientColor5
                    5 -> GradientColor6
                    6 -> GradientColor7
                    else -> GradientColor7
                },
                contentScreens = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White
                    ) {
                        when (index) {
                            0 -> ProjectsScreen()
                            1 -> TemplatesScreen()
                            2 -> TemplateUpsertScreen(onCallBack = {})
                            3 -> TemplateStatusScreen(
                                onDelete = {},
                                onChangeTemplateStatusName = {},
                                onChangeTemplateStatusColor = {}
                            )
                            4 -> ProjectUpsertScreen(
                                projectsEntity = null,
                                onDeleteProject = { },
                            )
                            5 -> AppText("EmojiEvents Content", fontSize = 13.sp)
                            6 -> AppText("Call Content", fontSize = 13.sp)
                            else -> AppText("Other", fontSize = 13.sp)
                        }
                    }
                }
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ProjectManagementMainScreenPreview() {
    ProjectManagementMainScreen()
}


