import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DraggablePager() {
    val pagerState = rememberPagerState(pageCount = { 2 })
    var items by remember { mutableStateOf(listOf(listOf("Item 1", "Item 2"), listOf("Item 3", "Item 4"))) }
    var draggedItem by remember { mutableStateOf<String?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, contentPadding = PaddingValues(end = 100.dp)) { page ->
            Column(Modifier.fillMaxSize()) {
                items[page].forEach { item ->
                    DraggableItem(
                        item = item,
                        onDragStart = { draggedItem = item; isDragging = true },
                        onDragEnd = {
                            if (isDragging) {
                                val dropPage = pagerState.currentPage
                                if (draggedItem != null) {
                                    items = items.mapIndexed { index, list ->
                                        if (index == items.indexOfFirst { it.contains(draggedItem) }) {
                                            list.filter { it != draggedItem }
                                        } else if (index == dropPage) {
                                            list + draggedItem!!
                                        } else {
                                            list
                                        }
                                    }
                                    draggedItem = null
                                    isDragging = false
                                }
                            }
                        },
                        onDrag = { dragAmount ->
                            dragOffset += dragAmount
                        }
                    )
                }
            }
        }

        if (isDragging && draggedItem != null) {
            Card(
                modifier = Modifier
                    .offset(dragOffset.x.dp, dragOffset.y.dp)
                    .graphicsLayer {
                        translationX = dragOffset.x
                        translationY = dragOffset.y
                        shadowElevation = 8.dp.toPx()
                    }
            ) {
                Text(text = draggedItem!!, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun DraggableItem(
    item: String,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (Offset) -> Unit
) {
    var itemOffset by remember { mutableStateOf(Offset.Zero) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .offset { itemOffset.toIntOffset() }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { onDragStart() },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        itemOffset += dragAmount
                        onDrag(itemOffset)
                    },
                    onDragEnd = {
                        onDragEnd()
                        itemOffset = Offset.Zero
                    },
                    onDragCancel = {
                        itemOffset = Offset.Zero
                        onDragEnd()
                    }
                )
            }
    ) {
        Text(text = item, modifier = Modifier.padding(16.dp))
    }
}

private fun Offset.toIntOffset(): androidx.compose.ui.unit.IntOffset {
    return androidx.compose.ui.unit.IntOffset(x.toInt(), y.toInt())
}