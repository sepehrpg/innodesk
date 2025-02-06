package com.example.common.permissions.lib1

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

/**
 * State of the [PermissionHandlerHost], which controls the queue and the current permission call
 * being handled inside the [PermissionHandlerHost].
 * This state is usually remembered and used to provide a [PermissionHandlerHost] to a Composable.
 *
 * @param permissionList list of permissions to be handled during [handlePermissions] call
 */
@ExperimentalPermissionHandlerApi
class PermissionHandlerHostState(private val permissionList: List<String>) {

    /**
     * State of the [PermissionHandlerHost], which controls the queue and the current permission call
     * being handled inside the [PermissionHandlerHost].
     * This state is usually remembered and used to provide a [PermissionHandlerHost] to a Composable.
     *
     * @param permission permission to be handled during [handlePermissions] call
     */
    constructor(permission: String) : this(permissionList = listOf(permission))

    /**
     * Only one permission request can be shown at a time.
     */
    private val mutex = Mutex()

    /**
     * The current [PermissionHandlerData] being handled by the [PermissionHandlerHost],
     * or `null` if none.
     */
    internal var currentPermissionHandlerData by mutableStateOf<PermissionHandlerData?>(null)

    /**
     * Handles or queues permissions request to be handled.
     *
     * [PermissionHandlerHostState] guarantees to handle at most one permission request at a time.
     * If this function is called while another permissions are being handled, it will be suspended
     * until this permission request is resolved.
     *
     * @return [PermissionHandlerResult.GRANTED] if permissions were granted.
     * [PermissionHandlerResult.DENIED] if permissions have been denied (because dialog was closed,
     * user clicked deny or dialog wasn't displayed by the Android system). Usually, we might want
     * to communicate this result to user (for instance, via Snackbar).
     * [PermissionHandlerResult.DENIED_NEXT_RATIONALE] if permissions have been denied, but next
     * request will have [Activity.shouldShowRationale] = true.
     * In this case, we're sure there will be one more opportunity to show permission request
     * dialog. Usually, we don't want to do anything here.
     */
    suspend fun handlePermissions(): PermissionHandlerResult =
        mutex.withLock {
            try {
                return suspendCancellableCoroutine { continuation ->
                    currentPermissionHandlerData =
                        PermissionHandlerDataImpl(
                            continuation,
                            permissionList
                        )
                }
            } finally {
                currentPermissionHandlerData = null
            }
        }

    private class PermissionHandlerDataImpl(
        private val continuation: CancellableContinuation<PermissionHandlerResult>,
        override val permissionList: List<String>,
        override val permissionState: PermissionState = PermissionState.Handle
    ) : PermissionHandlerData {

        override fun copy(permissionState: PermissionState) =
            PermissionHandlerDataImpl(continuation, permissionList, permissionState)

        override fun granted() {
            if (continuation.isActive) continuation.resume(PermissionHandlerResult.GRANTED)
        }

        override fun denied(isNextRationale: Boolean) {
            if (continuation.isActive) {
                if (isNextRationale) continuation.resume(PermissionHandlerResult.DENIED_NEXT_RATIONALE)
                else continuation.resume(PermissionHandlerResult.DENIED)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as PermissionHandlerDataImpl

            if (continuation != other.continuation) return false
            if (permissionList != other.permissionList) return false
            if (permissionState != other.permissionState) return false

            return true
        }

        override fun hashCode(): Int {
            var result = continuation.hashCode()
            result = 31 * result + permissionList.hashCode()
            result = 31 * result + permissionState.hashCode()
            return result
        }
    }

    internal fun updatePermissionState(permissionState: PermissionState) {
        currentPermissionHandlerData = currentPermissionHandlerData?.copy(permissionState)
    }
}

/**
 * Host for permission requests to be handled based on [hostState].
 *
 * By default, this function doesn't show anything on rationale and just calls permission request.
 *
 * If you want to customize rationale you can pass your own Composable. It will have access to
 * permissionRequest and dismissRequest callbacks.
 *
 * @sample com.dawidraszka.composepermissionhandler.sample.SampleScreen
 *
 * @param hostState state of this component to handle permissions requests properly.
 * @param rationale Composable to display when [Activity.shouldShowRationale] = true.
 */
@ExperimentalPermissionHandlerApi
@Composable
fun PermissionHandlerHost(
    hostState: PermissionHandlerHostState,
    rationale: @Composable (permissionRequest: () -> Unit, dismissRequest: () -> Unit) -> Unit =
        { permissionRequest, _ -> SideEffect { permissionRequest() } }
) {
    val currentPermissionHandlerData = hostState.currentPermissionHandlerData ?: return

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsResult ->
            val permissionsGranted = permissionsResult.values.all { it }
            if (permissionsGranted) {
                currentPermissionHandlerData.granted()
            } else {
                hostState.updatePermissionState(PermissionState.Deny)
            }
        }

    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()

    when (val permissionState = currentPermissionHandlerData.permissionState) {
        PermissionState.Deny ->
            currentPermissionHandlerData.denied(
                isNextRationale = activity.shouldShowRationale(currentPermissionHandlerData.permissionList)
            )
        PermissionState.Handle -> {
            if (activity.shouldShowRationale(currentPermissionHandlerData.permissionList)) {
                rationale(
                    permissionRequest = {
                        hostState.updatePermissionState(
                            PermissionState.HideRationale(
                                PermissionState.PermissionAction.Request
                            )
                        )
                    }, dismissRequest = {
                        hostState.updatePermissionState(
                            PermissionState.HideRationale(
                                PermissionState.PermissionAction.Dismiss
                            )
                        )
                    }
                )
            } else {
                SideEffect {
                    coroutineScope.launch {
                        launcher.launch(currentPermissionHandlerData.permissionList.toTypedArray())
                    }
                }
            }
        }
        is PermissionState.HideRationale -> {
            if (permissionState.permissionAction == PermissionState.PermissionAction.Dismiss) {
                currentPermissionHandlerData.denied(isNextRationale = true)
            } else {
                SideEffect {
                    coroutineScope.launch {
                        launcher.launch(currentPermissionHandlerData.permissionList.toTypedArray())
                    }
                }
            }
        }
    }
}

/**
 * Possible results of the [PermissionHandlerHostState.handlePermissions] call
 */
@ExperimentalPermissionHandlerApi
enum class PermissionHandlerResult {
    /**
     * Permissions were granted.
     */
    GRANTED,

    /**
     * Permissions have been denied - because dialog was closed, user clicked deny or dialog wasn't
     * displayed by the Android system). Usually, we might want to communicate this result to user
     * (for instance, via Snackbar).
     */
    DENIED,

    /**
     * Permissions have been denied, but next request will have
     * [Activity.shouldShowRationale] = true.
     * In this case, we're sure there will be one more opportunity to show permission request
     * dialog. Usually, we don't want to do anything with this result.
     */
    DENIED_NEXT_RATIONALE
}

internal sealed class PermissionState {
    object Handle : PermissionState()
    data class HideRationale(val permissionAction: PermissionAction) : PermissionState()
    object Deny : PermissionState()

    enum class PermissionAction {
        Request, Dismiss
    }
}

internal interface PermissionHandlerData {
    val permissionList: List<String>
    val permissionState: PermissionState
    fun copy(permissionState: PermissionState = this.permissionState): PermissionHandlerData
    fun granted()
    fun denied(isNextRationale: Boolean)
}