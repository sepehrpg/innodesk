
package com.innodesk.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.innodesk.app.ui.AppState
import com.innodesk.project_management.navigation.ProjectManagementIntroRoute
import com.innodesk.project_management.navigation.navigateToWorkSpaceScreen
import com.innodesk.project_management.navigation.projectManagementScreen


@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackBar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ProjectManagementIntroRoute,
        modifier = modifier,
    ) {

        projectManagementScreen(
            onNavigateToWorkSpaceScreen = navController::navigateToWorkSpaceScreen,
            onShowSnackBar = onShowSnackBar,
            onBackClickFromWorkSpaceScreen = navController::popBackStack
        )

    }
}
