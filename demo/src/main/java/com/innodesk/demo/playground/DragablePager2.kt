package com.innodesk.demo.playground


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp



@Composable
fun DragAndDropPager() {
    val pagerState = rememberPagerState(pageCount = {2})
    var draggedItem by remember { mutableStateOf<String?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    var itemsInPage1 by remember { mutableStateOf(listOf("Item 1", "Item 2", "Item 3")) }
    var itemsInPage2 by remember { mutableStateOf(listOf("Item A", "Item B", "Item C")) }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize().safeContentPadding()
    ) { page ->
        when (page) {
            0 -> PageContent(
                items = itemsInPage1,
                onItemDragStart = { item, offset ->
                    draggedItem = item
                    dragOffset = offset
                },
                onItemDragEnd = { offset ->
                    dragOffset = Offset.Zero
                    if (pagerState.currentPage == 1 && draggedItem != null) {
                        itemsInPage1 = itemsInPage1.filter { it != draggedItem }
                        itemsInPage2 = itemsInPage2 + draggedItem!!
                    }
                    draggedItem = null
                },
                onDragUpdate = { dragAmount ->
                    dragOffset += dragAmount
                }
            )
            1 -> PageContent(
                items = itemsInPage2,
                onItemDragStart = { item, offset ->
                    draggedItem = item
                    dragOffset = offset
                },
                onItemDragEnd = { offset ->
                    dragOffset = Offset.Zero
                    if (pagerState.currentPage == 0 && draggedItem != null) {
                        itemsInPage2 = itemsInPage2.filter { it != draggedItem }
                        itemsInPage1 = itemsInPage1 + draggedItem!!
                    }
                    draggedItem = null
                },
                onDragUpdate = { dragAmount ->
                    dragOffset += dragAmount
                }
            )
        }
    }
}

@Composable
fun PageContent(
    items: List<String>,
    onItemDragStart: (String, Offset) -> Unit,
    onItemDragEnd: (Offset) -> Unit,
    onDragUpdate: (Offset) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray, CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onItemDragStart(item, offset) },
                            onDragEnd = { onItemDragEnd(Offset.Zero) },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                onDragUpdate(dragAmount)
                            }
                        )
                    }
            ) {
                Text(
                    text = item,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black
                )
            }
        }
    }
}
