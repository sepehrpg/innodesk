package com.innodesk.project_management.workspace

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.di.qualifier.OfflineRepository
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.data.repository.project_management.tasks.TasksRepository
import com.example.data.repository.project_management.templates.TemplatesStatusRepository
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import com.example.database.model.pm.task.TasksEntity
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.extension.toHexString
import com.innodesk.project_management.navigation.WorkSpaceScreenRoute
import com.innodesk.project_management.templates_statuses.TemplateStatusUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class WorkSpaceUiState(
    val projectEntity: ProjectsEntity? = null,
    val templateEntity: TemplatesEntity? = null,
    val templateStatusesList : MutableList<TemplatesStatusEntity> = mutableListOf(),
    val tasksList : MutableList<TasksEntity> = mutableListOf(),

    val templateStatusColor:String? = null,
    val templateStatusName:String? = null,

)


@HiltViewModel
class WorkSpaceViewModel @Inject constructor(
    @OfflineRepository private val projectOfflineRepository: ProjectsRepository,
    @OfflineRepository private val templateStatusRepository: TemplatesStatusRepository,
    @OfflineRepository private val tasksRepository: TasksRepository,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {


    private val workSpaceScreenRoute = savedStateHandle.toRoute<WorkSpaceScreenRoute>()

    private val _uiState: MutableStateFlow<WorkSpaceUiState> = MutableStateFlow(
        WorkSpaceUiState()
    )
    val uiState: StateFlow<WorkSpaceUiState> = _uiState.asStateFlow()

    val projectWithTemplateAndStatuses: Flow<ProjectWithTemplateAndStatuses?> =
        projectOfflineRepository.getProjectWithTemplateAndStatuses(workSpaceScreenRoute.projectID)




    init {
        viewModelScope.launch {
            projectWithTemplateAndStatuses.collect{
                Timber.d(it.toString())

                updateProjectEntity(it?.project)
                updateTemplateEntity(it?.templateWithStatuses?.template)
                updateTemplateStatusesList(it?.templateWithStatuses?.statuses?.sortedBy { it.order }?.toMutableList()?: mutableListOf())

            }

            getTasksList(projectId = _uiState.value.projectEntity?.id)
        }
    }




    fun updateProjectEntity(projectEntity: ProjectsEntity?){
        _uiState.update { it.copy(projectEntity = projectEntity) }
    }

    fun updateTemplateEntity(templateEntity: TemplatesEntity?){
        _uiState.update { it.copy(templateEntity = templateEntity) }
    }

    fun updateTemplateStatusesList(templateStatusesList: MutableList<TemplatesStatusEntity>){
        _uiState.update { it.copy(templateStatusesList = templateStatusesList) }
    }

    fun updateTasksList(tasksList: MutableList<TasksEntity>){
        _uiState.update { it.copy(tasksList = tasksList) }
    }

    fun updateTemplateStatusName(name: String) {
        _uiState.update {
            it.copy(templateStatusName = name)
        }
    }

    fun updateTemplateStatusColor(color: Color) {
        _uiState.update { it.copy(templateStatusColor = color.toHexString()) }
    }

    fun clearData(){
        _uiState.update {
            it.copy(
                templateStatusName = null,
                templateStatusColor = null
            )
        }
    }



    private fun validationTemplateStatus(update:Boolean = false) : Boolean {
        if (_uiState.value.templateStatusName.isNullOrEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Insert Template Status name", snackBarType = SnackBarType.ERROR)
            }
            return false
        }
        if (_uiState.value.templateStatusColor.isNullOrEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Insert Template Status Color", snackBarType = SnackBarType.ERROR)
            }
            return false
        }

        if (!update && _uiState.value.projectEntity==null){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Project Empty", snackBarType = SnackBarType.ERROR)
            }
            return false
        }

        return true
    }
    fun databaseInsertTemplateStatus() : Boolean{
        if (!validationTemplateStatus()) return false

        viewModelScope.launch {
            val maxOrder:Int = _uiState.value.templateStatusesList.maxOfOrNull { it.order }?:0
            val item = TemplatesStatusEntity(
                name = _uiState.value.templateStatusName?:"",
                color = _uiState.value.templateStatusColor?:"",
                order = if(_uiState.value.templateStatusesList.isNotEmpty()) maxOrder + 1 else 0 ,
                templateId = _uiState.value.projectEntity?.templateId?:0 ,
            )
            templateStatusRepository.insertTemplateStatus(item)
        }

        return true
    }
    fun databaseUpdateTemplateStatus(templateStatus:TemplatesStatusEntity?) : Boolean{
        if (!validationTemplateStatus(true)) return false

        viewModelScope.launch {
            if (templateStatus!=null){
                val item = templateStatus.copy(
                    name = _uiState.value.templateStatusName?:templateStatus.name,
                    color = _uiState.value.templateStatusColor?:templateStatus.color,
                )
                templateStatusRepository.updateTemplateStatus(item)
            }
            else{
                SnackBarManager.showSnackBar(snackBarType = SnackBarType.ERROR, message = "ERROR")
            }
        }


        return true
    }
    fun databaseDeleteTemplateStatus(templateStatus:TemplatesStatusEntity?) : Boolean{
        viewModelScope.launch {
            if (templateStatus!=null){
                templateStatusRepository.deleteTemplateStatus(templateStatus)

            }
            else{
                SnackBarManager.showSnackBar(snackBarType = SnackBarType.ERROR, message = "ERROR")
            }
        }
        return true
    }


    private fun getTasksList(templateStatusId:Int?=null,projectId:Int?=null){
        viewModelScope.launch {
            tasksRepository.tasksList(templateStatusId,projectId).collect{
                updateTasksList(it.toMutableList())
            }
        }
    }

}