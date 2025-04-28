package com.innodesk.project_management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.innodesk.project_management.ProjectManagementScreen
import com.innodesk.project_management.workspace.screen.WorkSpaceScreen
import kotlinx.serialization.Serializable




@Serializable object ProjectManagementIntroRoute

@Serializable object ProjectManagementRoute
@Serializable data class WorkSpaceScreenRoute(val projectID: Int)

fun NavController.navigateToWorkSpaceScreen(projectID: Int) =
    navigate(route = WorkSpaceScreenRoute(projectID = projectID))

fun NavGraphBuilder.projectManagementScreen(
    onShowSnackBar: suspend (String, String?) -> Boolean,
    onNavigateToWorkSpaceScreen: (projectID:Int) -> Unit,
    onBackClickFromWorkSpaceScreen:()->Unit
) {

    navigation<ProjectManagementIntroRoute>(startDestination = ProjectManagementRoute){

        composable<ProjectManagementRoute> {
            ProjectManagementScreen(onNavigateToWorkSpaceScreen=onNavigateToWorkSpaceScreen)
        }

        composable<WorkSpaceScreenRoute> {
            WorkSpaceScreen(onBackClick = onBackClickFromWorkSpaceScreen)
        }

    }
}