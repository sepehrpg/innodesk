package com.innodesk.demo.playground



// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.designsystem.component.AppText
import kotlin.math.roundToInt


// Data model for an item.
data class Item(val id: Int, val content: String)

// ViewModel to manage the two lists and current drag item.
class DragDropViewModel : ViewModel() {
    // Sample lists: Page 1 starts with some items, Page 2 is initially empty.
    var page1Items by mutableStateOf(
        listOf(
            Item(1, "Item 1"),
            Item(2, "Item 2"),
            Item(3, "Item 3")
        )
    )
        private set

    var page2Items by mutableStateOf(listOf<Item>())
        private set

    // Currently dragging item (if any)
    var draggingItem by mutableStateOf<Item?>(null)

    // Remove item from Page 1 and add it to Page 2.
    fun moveItemToPage2(item: Item) {
        page1Items = page1Items.filter { it.id != item.id }
        page2Items = page2Items + item
        draggingItem = null
    }
}

// Composable for a draggable item.
@Composable
fun DraggableItem(
    item: Item,
    viewModel: DragDropViewModel,
    modifier: Modifier = Modifier
) {
    // Track the offset while dragging.
    var offset by remember { mutableStateOf(Offset.Zero) }

    // The Box displays the content and handles drag gestures.
    Box(
        modifier = modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .pointerInput(item.id) {
                detectDragGestures(
                    onDragStart = {
                        // When drag starts, mark the item as being dragged.
                        viewModel.draggingItem = item
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                    },
                    onDragEnd = {
                        // When the drag gesture ends, reset the offset.
                        offset = Offset.Zero
                    },
                    onDragCancel = {
                        offset = Offset.Zero
                        viewModel.draggingItem = null
                    }
                )
            }
    ) {
        AppText(text = item.content, color = Color.Black)
    }
}

// PageScreen displays a header and a LazyColumn of draggable items.
// It wraps its content in a drop area that detects pointer-up events.
@Composable
fun PageScreen(
    items: List<Item>,
    viewModel: DragDropViewModel,
    // onItemDropped is called when a drop is detected on this page.
    onItemDropped: (Item) -> Unit,
    header: String,
    // Enable drop detection for this page (for example, only Page 2 should react to drops).
    enableDrop: Boolean = false
) {
    // Drop area: if enabled, we intercept pointer up events.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (enableDrop) {
                    Modifier.pointerInput(Unit) {
                        // Loop to await pointer events.
                        while (true) {
                            // Wait for the first down event.
                            awaitPointerEventScope {
                                awaitFirstDown()
                                // Wait for a pointer event.
                                val event = awaitPointerEvent()
                                // If any pointer changed to Up, consider it as a drop.
                                if (event.changes.any { it.changedToUp() } && viewModel.draggingItem != null) {
                                    onItemDropped(viewModel.draggingItem!!)
                                }
                            }
                        }
                    }
                } else Modifier
            )
    ) {
        Column {
            AppText(
                text = header,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Black
            )
            // LazyColumn displays the list of draggable items.
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(items) { item ->
                    DraggableItem(item = item, viewModel = viewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// The HorizontalPager that hosts two pages.
@Composable
fun DragDropPagerDemo(viewModel: DragDropViewModel = viewModel()) {
    // Create pager state for 2 pages.
    val pagerState = rememberPagerState(pageCount = { 2 })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 100.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // optional padding between pages
    ) { page ->
        when (page) {
            0 -> {
                // Page 1 shows the list of draggable items.
                PageScreen(
                    items = viewModel.page1Items,
                    viewModel = viewModel,
                    header = "Page 1 - Drag from here",
                    onItemDropped = { /* no drop action on Page 1 */ },
                    enableDrop = false
                )
            }
            1 -> {
                // Page 2 is the drop area. When an item is dropped here,
                // move it from Page 1 to Page 2.
                PageScreen(
                    items = viewModel.page2Items,
                    viewModel = viewModel,
                    header = "Page 2 - Drop here",
                    onItemDropped = { item ->
                        if (viewModel.page1Items.any { it.id == item.id }) {
                            viewModel.moveItemToPage2(item)
                        }
                    },
                    enableDrop = true
                )
            }
        }
    }
}



