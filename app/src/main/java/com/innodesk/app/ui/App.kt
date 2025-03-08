
package com.innodesk.app.ui

import android.app.Activity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import                                                                                                                                  androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.designsystem.component.AppBackground
import com.example.designsystem.component.AppGradientBackground
import com.example.designsystem.component.AppSnackBarPrimary
import com.example.designsystem.component.SnackBarInDialogContainer
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.theme.LocalGradientColors
import com.innodesk.project_management.ProjectManagementMainScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KClass

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {

    AppBackground(modifier = modifier) {
        AppGradientBackground(
            gradientColors = LocalGradientColors.current
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = "Connection Lost"
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            App(
                appState = appState,
                snackbarHostState = snackbarHostState,
                windowAdaptiveInfo = windowAdaptiveInfo,
            )
        }
    }
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)
internal fun App(
    appState: AppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {

    val activityContext = LocalActivity.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    var snackBarMessage by rememberSaveable { mutableStateOf("") }
    var snackBarType by rememberSaveable { mutableStateOf(SnackBarType.DEFAULT) }
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Timber.tag("keyboardController").d(imeVisible.toString())

    LaunchedEffect(SnackBarManager.snackBarFlow) {
        SnackBarManager.snackBarFlow.collectLatest { data ->
            Timber.tag("SnackBarHost").d("${data.message} ---- ${data.snackBarType}")
            Timber.tag("keyboardController").d(keyboardController?.hide().toString())

            hideKeyboard(activityContext!!)

            snackBarMessage = data.message
            snackBarType = data.snackBarType
            snackbarHostState.showSnackbar("")
        }
    }



    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier,
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    Dialog(
                        onDismissRequest = {},
                        properties = DialogProperties(
                            dismissOnClickOutside = false,
                            dismissOnBackPress = false,
                            usePlatformDefaultWidth = false
                        )
                    ) {
                        (LocalView.current.parent as DialogWindowProvider).window.apply {
                            setDimAmount(0f)
                            addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                            addFlags(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE)
                            addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                        }
                        Box(){
                            AppSnackBarPrimary(
                                message = snackBarMessage,
                                snackBarType = snackBarType,
                                alignment = Alignment.TopCenter
                                //alignment = if (imeVisible) Alignment.Center else  Alignment.BottomCenter
                            ) { }
                            Timber.tag("SnackBarHost").d("$snackBarType----$snackBarMessage")
                        }
                    }
                }
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Box(
                Modifier.zIndex(1f)
            ) {
                ProjectManagementMainScreen()
            }

        }
    }
}


private fun hideKeyboard(context: Activity) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        context.getWindow().getDecorView().getWindowToken(),
        0
    )
}



private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
