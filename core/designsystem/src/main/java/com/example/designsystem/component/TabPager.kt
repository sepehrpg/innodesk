package com.example.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.Blue20
import com.example.designsystem.theme.Orange20
import kotlinx.coroutines.launch


/**
 *
 * A custom Composable for creating a tabbed interface with swipeable pages.
 *
 * @param tabs List of tab titles.
 * @param contentScreens List of Composable functions representing content screens for each tab.
 * @param modifier Modifier for the parent layout.
 * @param containerColor Background color for the tab row container.
 * @param contentColor Color for the text content of the tabs.
 * @param indicatorColor Color for the indicator line.
 *
 * EXAMPLE OF USAGE::
 *     val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
 *     val contentScreens: List<@Composable () -> Unit> = listOf(
 *         { Text("Content 1") },
 *         { Text("Content 2") },
 *         { Text("Content 3") },
 *     )
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppTabPager(
    tabs: List<String>,
    contentScreens: List<@Composable () -> Unit>,
    screenModifier: Modifier = Modifier,
    tabRowModifier: Modifier = Modifier,
    tabModifier: Modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
    pagerModifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color.White,
    tabTextColor: Color = Color.Gray,
    tabTextColorSelected: Color = Color.Blue,
    indicatorColor: Color = Color.DarkGray,
    thicknessIndicator: Dp = 1.dp,
    dividerModifier: Modifier = Modifier,
    dividerColor: Color = DividerDefaults.color,
    dividerThickness: Dp = DividerDefaults.Thickness,
    scrollableTab:Boolean = false
) {
    // Remember the PagerState for managing page selection and transitions
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { contentScreens.size })
    val coroutineScope = rememberCoroutineScope()

    // Column layout to arrange tabs vertically and display content screens
    Column(modifier = screenModifier.fillMaxSize()) {
        // ScrollableTabRow composable to display tabs
        if (scrollableTab){
            ScrollableTabRow(
                modifier = tabRowModifier,
                selectedTabIndex = pagerState.currentPage,
                containerColor = containerColor,
                contentColor = contentColor,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    // Indicator for the selected tab
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = indicatorColor,
                        height = thicknessIndicator
                    )
                },
                divider = {
                    HorizontalDivider(
                        modifier = dividerModifier,
                        color = dividerColor,
                        thickness = dividerThickness
                    )
                }
            ) {
                // Iterate through each tab title and create a tab
                tabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        modifier = tabModifier,
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // Scroll to the clicked tab's page
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        // Text displayed on the tab
                        AppText(
                            text = tabTitle,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (pagerState.currentPage == index) tabTextColorSelected else tabTextColor,
                        )
                    }
                }
            }
        }
        else{
            TabRow(
                modifier = tabRowModifier,
                selectedTabIndex = pagerState.currentPage,
                containerColor = containerColor,
                contentColor = contentColor,
                indicator = { tabPositions ->
                    // Indicator for the selected tab
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = indicatorColor,
                        height = thicknessIndicator
                    )
                },
                divider = {
                    HorizontalDivider(modifier= dividerModifier , color = dividerColor, thickness = dividerThickness)
                }
            ) {
                // Iterate through each tab title and create a tab
                tabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        modifier = tabModifier,
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // Scroll to the clicked tab's page
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        // Text displayed on the tab
                        AppText(
                            text = tabTitle,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if(pagerState.currentPage==index) tabTextColorSelected else tabTextColor,
                        )
                    }
                }
            }
        }
        // HorizontalPager to swipe between content screens
        HorizontalPager(
            state = pagerState,
            modifier = pagerModifier.weight(1f),
        ) { page ->
            // Display the content screen corresponding to the current page
            contentScreens[page]()
        }
    }
}









@ThemePreviews
@Composable
fun AppTabPagerPreview(){
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
    val contentScreens: List<@Composable () -> Unit> = listOf(
        { LazyColumn {
            item {
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
                Text(text = ("HELLO WORLD"), fontSize = 30.sp)
            }
        } },
        { Text("") },
        { Text("") },
    )


    Box(Modifier.fillMaxSize()){
        AppTabPager(
            tabs = tabs,
            contentScreens = contentScreens,
            containerColor = Color.White,
            indicatorColor = Orange20,
            tabTextColor = Color.LightGray,
            tabTextColorSelected = Blue20,
            thicknessIndicator = 2.dp,
            dividerThickness = 0.5.dp,
        )
    }
}