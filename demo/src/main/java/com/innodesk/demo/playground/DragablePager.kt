package com.innodesk.demo.playground

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs


internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }
internal class DragTargetInfo {
    var isDragging: Boolean? by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
    var itemDropped: Boolean by mutableStateOf(false)
    var absolutePositionX: Float by mutableStateOf(0F)
    var absolutePositionY: Float by mutableStateOf(0F)
}

@Composable
fun LongPressDraggable(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }

    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()
            if (state.isDragging == true) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = (state.dragPosition + state.dragOffset)
                        // will scale the dragged item being dragged by 50%
                        scaleX = 1.5f
                        scaleY = 1.5f
                        // adds a bit of transparency
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        // horizontal displacement
                        translationX = offset.x.minus(targetSize.width / 2)
                        // vertical displacement
                        translationY = offset.y.minus(targetSize.height / 2)
                    }
                    .onGloballyPositioned {
                        targetSize = it.size
                        it.let { coordinates ->
                            state.absolutePositionX = coordinates.positionInRoot().x
                            state.absolutePositionY = coordinates.positionInRoot().y
                        }
                    }
                ) {
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DragTarget(
    context: Context,
    pagerSize: Int,
    verticalPagerState: PagerState? = null, // if you have nested / multi paged app
    horizontalPagerState: PagerState? = null,
    modifier: Modifier,
    dataToDrop: Any? = null, // change type here to your data model class
    content: @Composable (shouldAnimate: Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(modifier = modifier
        .onGloballyPositioned {
            currentPosition = it.localToWindow(Offset.Zero)
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = {

                    currentState.dataToDrop = dataToDrop
                    currentState.isDragging = true
                    currentState.dragPosition = currentPosition + it
                    currentState.draggableComposable = {
                        content(false) // render scaled item without animation }

                    }
                }, onDrag = { change, dragAmount ->
                    change.consume()

                    currentState.itemDropped =
                        false // used to prevent drop target from multiple re-renders

                    currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)

                    val xOffset = abs(currentState.dragOffset.x)
                    val yOffset = abs(currentState.dragOffset.y)

                    coroutineScope.launch {

                        // this is a flag only for demo purposes, change as per your needs
                        val boundDragEnabled = false

                        if (boundDragEnabled) {
                            // use this for dragging after the user has dragged the item outside a bound around the original item itself
                            if (xOffset > 20 && yOffset > 20) {
                                verticalPagerState?.animateScrollToPage(
                                    1,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = androidx.compose.animation.core.EaseOutCirc
                                    )
                                )
                            }
                        } else {
                            // for dragging to and fro from different pages in the pager
                            val currentPage = horizontalPagerState?.currentPage
                            val dragPositionX =
                                currentState.dragPosition.x + currentState.dragOffset.x
                            val dragPositionY =
                                currentState.dragPosition.y + currentState.dragOffset.y

                            val displayMetrics = context.resources.displayMetrics

                            // if item is very close to left edge of page, move to previous page
                            if (dragPositionX < 60) {
                                currentPage?.let {
                                    if (it > 1) {
                                        horizontalPagerState.animateScrollToPage(currentPage - 1)
                                    }
                                }
                            } else if (displayMetrics.widthPixels - dragPositionX < 60) {
                                // if item is very close to right edge of page, move to next page
                                currentPage?.let {
                                    if (it < pagerSize) {
                                        horizontalPagerState.animateScrollToPage(currentPage + 1)
                                    }
                                }
                            }
                        }
                    }

                }, onDragEnd = {
                    currentState.isDragging = false
                    currentState.dragOffset = Offset.Zero
                }, onDragCancel = {
                    currentState.isDragging = false
                    currentState.dragOffset = Offset.Zero
                })
        }, contentAlignment = Alignment.Center
    ) {
        content(true) // render positioned content with animation
    }
}

@Composable
fun <T> DropTarget(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInWindow().let { rect ->
                    isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
                }
            }
    ) {
        val data =
            if (isCurrentDropTarget && dragInfo.isDragging == false) dragInfo.dataToDrop as T? else null
        content(isCurrentDropTarget, data)
    }
}




@Composable
fun HorizontalPagerContent() {
    val pagerState = rememberPagerState(pageCount = {2})

    // Wrap the entire horizontal pager with LongPressDraggable
    LongPressDraggable {
        HorizontalPager(state = pagerState) { pageIndex ->
            when (pageIndex) {
                0 -> Page1Content(pagerState)
                1 -> Page2Content(pagerState)
            }
        }
    }
}


@Composable
fun Page1Content(pagerState: PagerState) {
    val widgetList = mutableListOf(
        Widget(name = "Sepehr","DES"),
        Widget(name = "Melika","DES"),
        Widget(name = "Sahar","DES"),
        Widget(name = "Paniz","DES"),
        Widget(name = "Kimia","DES"),
        Widget(name = "Sina","DES"),
    )

    DropTarget<Widget>(modifier = Modifier.fillMaxSize())
    { isInBound, droppedWidget ->
        if (!LocalDragTargetInfo.current.itemDropped) {
            if (isInBound) {
                droppedWidget?.let { widget ->
                    LocalDragTargetInfo.current.itemDropped = true
                    LocalDragTargetInfo.current.dataToDrop = null

                    val currentlyPlacedItem = getCurrentlyPlacedItemInList()
                    // Use pagerState, LocalDragTargetInfo.current.absolutePositionX,
                    // LocalDragTargetInfo.current.absolutePositionY to determine what's
                    // currently placed in the list and make changes to the list accordingly

                    // Example: If nothing is currently placed at the drop position, add the dropped widget to the list
                    if (currentlyPlacedItem == null) {
                        addWidgetToList(widget)
                    } else {
                        // Example: Swap the currently placed item with the dropped widget
                        moveWidgets(widget, currentlyPlacedItem)
                    }
                }
            }
        }
    }
    WidgetsList(pagerState, widgetList)
}

@Composable
fun Page2Content(pagerState: PagerState) {
    val widgetList = mutableListOf(
        Widget(name = "LALAL","DES"),
    )

    DropTarget<Widget>(modifier = Modifier.fillMaxSize())
    { isInBound, droppedWidget ->
        if (!LocalDragTargetInfo.current.itemDropped) {
            if (isInBound) {
                droppedWidget?.let { widget ->
                    LocalDragTargetInfo.current.itemDropped = true
                    LocalDragTargetInfo.current.dataToDrop = null

                    val currentlyPlacedItem = getCurrentlyPlacedItemInList()
                    // Use pagerState, LocalDragTargetInfo.current.absolutePositionX,
                    // LocalDragTargetInfo.current.absolutePositionY to determine what's
                    // currently placed in the list and make changes to the list accordingly

                    // Example: If nothing is currently placed at the drop position, add the dropped widget to the list
                    if (currentlyPlacedItem == null) {
                        addWidgetToList(widget)
                    } else {
                        // Example: Swap the currently placed item with the dropped widget
                        moveWidgets(widget, currentlyPlacedItem)
                    }
                }
            }
        }
    }
    WidgetsList(pagerState, widgetList)
}


@Composable
fun WidgetsList(pagerState: PagerState, widgetList: List<Widget>){
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(widgetList) { widget ->
            // this composable was defined earlier as
            // each widget item is itself a drag target
            DragTargetWidgetItem(
                data = widget,
                pagerState = pagerState
            )
        }
    }
}



@Composable
fun DragTargetWidgetItem(
    data: Widget,
    pagerState: PagerState
) {
    DragTarget(
        context = LocalContext.current,
        pagerSize = 2, // Assuming there are two pages in the horizontal pager
        horizontalPagerState = pagerState,
        modifier = Modifier.wrapContentSize(),
        dataToDrop = data,
    ) { shouldAnimate ->
        WidgetItem(data, shouldAnimate)
    }
}

@Composable
fun WidgetItem(
    data: Widget,
    shouldAnimate: Boolean
) {
    // Add your custom implementation for the WidgetItem here.
    // This composable will render the content of the draggable widget.
    // You can use the 'data' parameter to extract necessary information and display it.
    // The 'shouldAnimate' parameter can be used to control animations if needed.

    // Example: Displaying a simple card with the widget's name
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer {
                // Scale the card when shouldAnimate is true
                scaleX = if (shouldAnimate) 1.2f else 1.0f
                scaleY = if (shouldAnimate) 1.2f else 1.0f
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = data.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = data.description)
        }
    }
}

// Implement the required functions to handle adding and swapping widgets in the list
fun addWidgetToList(widget: Widget) {
    // Add the dropped widget to the list
}

fun moveWidgets(widgetA: Widget, currentlyPlacedItem: Widget) {
    // Move items in the list
}

// Implement the required function to get the currently placed item in the list based on drop position
fun getCurrentlyPlacedItemInList(): Widget? {
    // Use pagerState, LocalDragTargetInfo.current.absolutePositionX,
    // LocalDragTargetInfo.current.absolutePositionY
    // to determine the currently placed item in the list
    // based on the drop position
    return null
}

data class Widget(val name: String, val description: String)


@Preview
@Composable
fun HorizontalPagerContentPreview(){
    HorizontalPagerContent()
}