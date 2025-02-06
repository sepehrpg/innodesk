package com.example.common.permissions.lib1.example

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.common.permissions.lib1.ExperimentalPermissionHandlerApi
import com.example.common.permissions.lib1.PermissionHandlerHost
import com.example.common.permissions.lib1.PermissionHandlerHostState
import com.example.common.permissions.lib1.PermissionHandlerResult
import com.example.common.permissions.src1.ExperimentalPermissionsApi
import com.example.common.permissions.src1.rememberPermissionState
import com.example.common.permissions.src1.shouldShowRationale
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionHandlerApi::class)
@Composable
fun SampleScreen() {
    val snackbarHostState = SnackbarHostState()

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.CAMERA
        //Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Declare permission host state and permission handler host, similarly to snackbar
    val permissionHandlerHostState = PermissionHandlerHostState(permission)
    PermissionHandlerHost(hostState = permissionHandlerHostState,
        // You don't have to specify rationale if you don't need it
        rationale = { permissionRequest, dismissRequest -> // Handy callbacks to make code concise
            AlertDialog(
                modifier = Modifier.padding(horizontal = 12.dp),
                onDismissRequest = dismissRequest,
                title = {
                    Text(text = "Permission Required!")
                },
                text = {
                    Text("This permission is required. Please grant the permission on the next popup.")
                },
                confirmButton = {
                    Button(onClick = permissionRequest) {
                        Text(text = "Ok")
                    }
                },
                dismissButton = {
                    Button(onClick = dismissRequest) {
                        Text(text = "Cancel")
                    }
                }
            )
        })

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Button(modifier = Modifier.padding(it), onClick = {
            coroutineScope.launch {
                // Hide the snackbar in case one is still being displayed
                snackbarHostState.currentSnackbarData?.dismiss()
                // Make the call to handle permissions
                when (permissionHandlerHostState.handlePermissions()) {
                    PermissionHandlerResult.GRANTED -> {
                        /* Do your action */
                        val snackbarResult = snackbarHostState.showSnackbar(
                            "App permission granted.",
                            "Settings",
                            duration = SnackbarDuration.Short
                        )
                    }
                    PermissionHandlerResult.DENIED -> {
                        // No need to check anything as library differentiates between denied state
                        // and denied, but next is a rationale
                        val snackbarResult = snackbarHostState.showSnackbar(
                            "App permission denied.",
                            "Settings",
                            duration = SnackbarDuration.Short
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed -> {
                                // Open app settings if snackbar's action was performed
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                //context.startActivity(intent)
                            }
                        }
                    }
                    PermissionHandlerResult.DENIED_NEXT_RATIONALE -> { /* Usually there's no need to
                    do anything here */ }
                }
            }
        }) {
            Text("Pick image")
        }
    }
}










@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SampleScreen2() {
    val snackbarHostState = SnackbarHostState()
    // Needed to determine whether to show snackbar or not
    var showGoToSettings by remember { mutableStateOf(false) }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val imagesPermissionState = rememberPermissionState(permission) { permissionState ->
        if (permissionState) {
            // Do your action
        } else {
            // If permission was denied for any reason, show the snackbar...
            showGoToSettings = true
        }
    }

    if (imagesPermissionState.status.shouldShowRationale) {
        // ... unless there is rationale available, in which case, override the flag
        showGoToSettings = false
    }

    var openRationaleDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    // Show snackbar if permissions were denied and there's no rationale to be displayed
    if (showGoToSettings) {
        LaunchedEffect(key1 = snackbarHostState) {
            val snackbarResult = snackbarHostState.showSnackbar(
                "App permission denied.",
                "Settings",
                duration = SnackbarDuration.Short
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> {
                    // Open app settings if snackbar's action was performed
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    context.startActivity(intent)
                }
            }
            showGoToSettings = false
        }
    }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        // Button which requests permissions (or 'launches' action specified in permission request
        // result callback
        Button(modifier = Modifier.padding(it), onClick = {
            coroutineScope.launch {
                // Hide the snackbar in case one is still being displayed
                snackbarHostState.currentSnackbarData?.dismiss()
                // If there is rationale to be displayed, set the flag. Otherwise, launch request.
                if (imagesPermissionState.status.shouldShowRationale) {
                    openRationaleDialog = true
                } else {
                    imagesPermissionState.launchPermissionRequest()
                }
            }
        }) {
            Text("Pick image")
        }
    }

    // If there is a rationale to be displayed, do that
    if (openRationaleDialog) {
        AlertDialog(
            modifier = Modifier.padding(horizontal = 12.dp),
            onDismissRequest = { openRationaleDialog = false },
            title = {
                Text(text = "Permission Required!")
            },
            text = {
                Text("This permission is required. Please grant the permission on the next popup.")
            },
            confirmButton = {
                Button(onClick = {
                    openRationaleDialog = false
                    imagesPermissionState.launchPermissionRequest()
                }) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                Button(onClick = { openRationaleDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


/**
 * OR CALL IN VIEWMODEL::
 *     @OptIn(ExperimentalPermissionHandlerApi::class)
 *     fun checkLocationsPermissions(permissionHandlerHostState: PermissionHandlerHostState){
 *         viewModelScope.launch {
 *             when(permissionHandlerHostState.handlePermissions()){
 *                 PermissionHandlerResult.GRANTED -> {
 *                     /* Permissions granted. Do your action here */
 *                     Log.i("LocationRequestsHandler","GRANTED")
 *
 *                 }
 *                 PermissionHandlerResult.DENIED -> {
 *                     /* Permissions were denied. Communicate to userdenial for instance, with a Snackbar */
 *                     Log.i("LocationRequestsHandler","DENIED")
 *
 *
 *                 }
 *                 PermissionHandlerResult.DENIED_NEXT_RATIONALE -> {
 *                     /* Permissions were denied, but there will be one more try with rationale. Usually, there's no need to do anything here. */
 *                     Log.i("LocationRequestsHandler","DENIED_NEXT_RATIONALE")
 *
 *                 }
 *             }
 *         }
 *     }
 */