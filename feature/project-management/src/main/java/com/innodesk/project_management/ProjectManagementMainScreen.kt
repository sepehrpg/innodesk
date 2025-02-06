package com.innodesk.project_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


@Composable
fun ProjectManagementMainScreen() {
    var gradientBackgroundColor by remember {
        mutableStateOf(GradientColor1)
    }

    AppTheme {
        AppGradientBackground(
            gradientColors = gradientBackgroundColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Body(
                changeBackgroundColor = {
                    it?.let { gradientBackgroundColor = it }
                }
            )
        }
    }
}




@Composable
private fun Body(
    changeBackgroundColor: (gradientBackgroundColor: GradientColors?) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var pageIndex by remember { mutableStateOf(0) }
    val tabItems = listOf(
        AppTabPagerItems(
            title = "Home",
            indicatorColor = GradientColor1.bottom,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(18.dp),
                    tint = if(pageIndex==0) GradientColor1.bottom else Color.Gray
                )
            },
            gradientColorsList = GradientColor1,
            contentScreens = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) { Text("Home Content", modifier = Modifier.padding(16.dp)) }
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
                    if(pageIndex==1) GradientColor2.bottom.adjustWarmth(50) else Color.Gray
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
                    tint = if(pageIndex==2) GradientColor3.bottom.adjustWarmth(50) else Color.Gray
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
                    tint = if(pageIndex==3) GradientColor4.bottom.adjustWarmth(50) else Color.Gray
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
                    tint = if(pageIndex==4) GradientColor5.bottom.adjustWarmth(50) else Color.Gray
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
                    tint = if(pageIndex==5) GradientColor6.bottom.adjustWarmth(50) else Color.Gray
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
                    tint = if(pageIndex==6) GradientColor7.bottom.adjustWarmth(50) else Color.Gray
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
    )

    Box(
        Modifier
            .fillMaxSize()
            .safeContentPadding()) {

        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 5.dp)) {
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
                    .padding(horizontal = 15.dp, vertical = 7.dp)){
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Box(Modifier.weight(1f)){
                        AppCustomSearchBarBasicTextField(
                            value = search,
                            onValueChange = { search = it },
                            shape = RoundedCornerShape(15.dp),
                            height = 45.dp,
                            brush = Brush.horizontalGradient(listOf(Color(0xFFEEEEEE),Color.White))
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Box{
                        AppFilledTonalIconButton(
                            onClick = { /* Handle click */ },
                            modifier = Modifier.size(45.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.DarkGray
                            ),
                            boxModifier = Modifier.shadow(4.dp,RoundedCornerShape(12.dp)).background(Brush.horizontalGradient(listOf(Color.White,Color(0xFFEEEEEE))),RoundedCornerShape(12.dp)),
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


            Box(Modifier.padding(top = 2.dp)){
                val mod: Modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .background(Color.White)
                AppTabPager(
                    tabs = tabItems,
                    //icons = icons, // comment out this line to preview without icons
                    //contentScreens = contentScreens,
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
                        changeBackgroundColor(tabItems[it].gradientColorsList)
                        pageIndex = it
                    }
                )
            }
        }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Image(
                painterResource(R.drawable.bottombar),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun ProjectManagementMainScreenPreview() {
    AppTheme {
        Body() {

        }
    }
}


fun Color.enhanceSaturation(factor: Float = 0.8f): Color {
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(this.toArgb(), hsv)
    hsv[1] = (hsv[1] * factor).coerceAtMost(1.0f) // Increase saturation
    //return Color(android.graphics.Color.HSVToColor(hsv))
    return this
}
fun Color.adjustWarmth(increase: Int, scaling: Float = 0.1f): Color {
    // Convert Color to ARGB int.
    val argb = this.toArgb()
    val a = (argb shr 24) and 0xff
    val r = (argb shr 16) and 0xff
    val g = (argb shr 8) and 0xff
    val b = argb and 0xff
    // Calculate the reductions with scaling.
    val redReduction = ((increase * 63 / 10f) * scaling).toInt()
    val greenReduction = ((increase * 78 / 10f) * scaling).toInt()

    val newR = (r - redReduction).coerceIn(0, 255)
    val newG = (g - greenReduction).coerceIn(0, 255)

    return Color((a shl 24) or (newR shl 16) or (newG shl 8) or b)
}