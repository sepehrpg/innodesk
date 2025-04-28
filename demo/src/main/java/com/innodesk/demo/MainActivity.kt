package com.innodesk.demo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.designsystem.component.AppSnackBarPrimary
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.innodesk.project_management.workspace.screen.WorkSpaceScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //@Inject
    //lateinit var database: RoomDb

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val dao = database.projectManagementDao()

        /*lifecycleScope.launch(Dispatchers.IO){
            val templates = dao.templatesList()
            Timber.d("DATABASE : ${templates}")
        }*/

        //viewModel.insertProjects()



        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val activityContext = LocalActivity.current
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val coroutineScope = rememberCoroutineScope()
            var snackBarMessage by rememberSaveable { mutableStateOf("") }
            var snackBarType by rememberSaveable { mutableStateOf(SnackBarType.DEFAULT) }
            val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
            LaunchedEffect(SnackBarManager.snackBarFlow) {
                SnackBarManager.snackBarFlow.collectLatest { data ->
                    Timber.tag("SnackBarHost").d("${data.message} ---- ${data.snackBarType}")
                    Timber.tag("keyboardController").d(keyboardController?.hide().toString())
                    snackBarMessage = data.message
                    snackBarType = data.snackBarType
                    snackbarHostState.showSnackbar("")
                }
            }

            Scaffold(
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
            ){
                val padding = it
                //ProjectManagementScreen()
                WorkSpaceScreen()

                /*Box(Modifier.safeContentPadding()) {
                    //DatabaseScreen()
                    //ProjectManagementScreen()
                    //WorkSpaceScreen()
                    //HorizontalPagerContent()
                    //DragDropPagerDemo()
                    //DragAndDropPager()
                    //DraggablePager()
                    //DragAndDropPager4()
                    HorizontalPagerContent()
                }*/
            }


        }

    }
}

