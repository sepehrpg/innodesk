package com.innodesk.project_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.rounded.UnfoldMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.extension.adjustWarmth
import com.example.designsystem.component.AppCustomSearchBarBasicTextField
import com.example.designsystem.component.AppFilledTonalIconButton
import com.example.designsystem.component.AppGradientBackground
import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.theme.AppTheme
import com.example.designsystem.theme.GradientColor1
import com.example.designsystem.theme.GradientColor2
import com.example.designsystem.theme.GradientColor3
import com.example.designsystem.theme.GradientColor4
import com.example.designsystem.theme.GradientColor5
import com.example.designsystem.theme.GradientColor6
import com.example.designsystem.theme.GradientColor7
import com.example.designsystem.theme.GradientColors
import com.innodesk.project_management.projects.ProjectsScreen


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
            Box(Modifier.fillMaxSize().then(if (isPreview) Modifier else Modifier.statusBarsPadding().navigationBarsPadding())) {
                Column(Modifier.fillMaxSize()) {
                    Column(Modifier.weight(1f)){
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
                Icon(
                    painterResource(com.example.designsystem.R.drawable.core_designsystem_user_add),
                    modifier = Modifier.size(22.dp),
                    contentDescription = ""
                )
            }
            Box(Modifier.weight(2f), contentAlignment = Alignment.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Sepehrpg", color = Color.Black, fontWeight = FontWeight.Bold)
                    Icon(
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
                    brush = Brush.horizontalGradient(listOf(Color(0xFFEEEEEE),Color.White))
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
                    Icon(
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
    var pageIndex by remember { mutableStateOf(0) }

    Box(Modifier.padding(top = 2.dp)) {
        val mod: Modifier = Modifier.shadow(2.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .clip(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .background(Color.White)
        AppTabPager(
            tabs = tabItem(pageIndex),
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
                changeBackgroundColor(tabItem(pageIndex)[it].gradientColorsList)
                pageIndex = it
            }
        )
    }
}



private fun tabItem(pageIndex:Int): List<AppTabPagerItems> = buildList {
    repeat(7) { index ->
        add(
            AppTabPagerItems(
                title = when (index) {
                    0 -> "Projects"
                    1 -> "Settings"
                    2 -> "Info"
                    3 -> "Star"
                    4 -> "Search"
                    5 -> "EmojiEvents"
                    else -> "Call"
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
                    Icon(
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
                            0 -> "Home"
                            1 -> "Settings"
                            2 -> "Info"
                            3 -> "Star"
                            4 -> "Search"
                            5 -> "EmojiEvents"
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
                            1 -> Text("Settings Content")
                            2 -> Text("Info Content")
                            3 -> Text("Star Content")
                            4 -> Text("Search Content")
                            5 -> Text("EmojiEvents Content")
                            6 -> Text("Call Content")
                            else -> Text("Other")
                        }
                    }


                }
            )
        )
    }


    /*val tabItems:List<AppTabPagerItems> = listOf(
        AppTabPagerItems(
            title = "Projects",
            indicatorColor = GradientColor1.bottom,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Work,
                    contentDescription = "Home",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 0) GradientColor1.bottom else Color.Gray
                )
            },
            gradientColorsList = GradientColor1,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ProjectsScreen()
                }
            }
        ),
        AppTabPagerItems(
            title = "Settings",
            indicatorColor = GradientColor2.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(18.dp),
                    if (pageIndex == 1) GradientColor2.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor2,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Settings Content", modifier = Modifier.padding(16.dp)) }
            }
        ),
        AppTabPagerItems(
            title = "Info",
            indicatorColor = GradientColor3.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 2) GradientColor3.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor3,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Info Content", modifier = Modifier.padding(16.dp)) }
            }
        ),
        AppTabPagerItems(
            title = "Star",
            indicatorColor = GradientColor4.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 3) GradientColor4.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor4,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Star Content", modifier = Modifier.padding(16.dp)) }
            }
        ),
        AppTabPagerItems(
            title = "Search",
            indicatorColor = GradientColor5.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 4) GradientColor5.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor5,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Search Content", modifier = Modifier.padding(16.dp)) }
            }
        ),
        AppTabPagerItems(
            title = "EmojiEvents",
            indicatorColor = GradientColor6.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = "EmojiEvents",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 5) GradientColor6.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor6,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("EmojiEvents Content", modifier = Modifier.padding(16.dp)) }
            }
        ),
        AppTabPagerItems(
            title = "Call",
            indicatorColor = GradientColor7.bottom.adjustWarmth(50),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Call",
                    modifier = Modifier.size(18.dp),
                    tint = if (pageIndex == 6) GradientColor7.bottom.adjustWarmth(50) else Color.Gray
                )
            },
            gradientColorsList = GradientColor7,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Call Content", modifier = Modifier.padding(16.dp)) }
            }
        )
    )*/
}




@Preview(showBackground = true)
@Composable
private fun ProjectManagementMainScreenPreview() {
    ProjectManagementMainScreen()
}


