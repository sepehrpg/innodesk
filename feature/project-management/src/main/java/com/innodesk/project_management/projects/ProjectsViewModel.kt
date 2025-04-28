package com.innodesk.project_management.projects

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.qualifier.OfflineRepository
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.database.model.pm.project.ProjectAccess
import com.example.database.model.pm.project.ProjectsEntity
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.extension.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class ProjectUiState(

    val projectName:String? = null,
    val projectAccessValue:ProjectAccess = ProjectAccess.PRIVATE,
    val projectColor:String? = null,

    val templateId: Int? = null,

)


@HiltViewModel
class ProjectsViewModel @Inject constructor(
    @OfflineRepository private val projectOfflineRepository: ProjectsRepository,

    ): ViewModel(){

    private val _uiState:MutableStateFlow<ProjectUiState> = MutableStateFlow(ProjectUiState())
    val uiState:StateFlow<ProjectUiState> = _uiState.asStateFlow()

    val projectsList: Flow<List<ProjectsEntity>> = projectOfflineRepository.projectsList()


   //Update Ui State
    //..............................................................................................
    fun updateProjectName(name: String) {
        _uiState.update {
            it.copy(projectName = name)
        }
       Timber.d(_uiState.value.projectName)
   }

    fun updateProjectAccessId(projectAccessValue: ProjectAccess) {
        Timber.d(projectAccessValue.toString())
        _uiState.update { it.copy(projectAccessValue = projectAccessValue) }
        Timber.d(_uiState.value.projectAccessValue.toString())
    }
    fun updateProjectColor(color: Color?) {
        _uiState.update { it.copy(projectColor = color?.toHexString()) }
        Timber.d(_uiState.value.projectColor)
    }
    //..............................................................................................


    //Validation
    //..............................................................................................
    private fun validationProjectEntity(update:Boolean = false):Boolean {
        if (_uiState.value.projectName.isNullOrEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Insert Project Name", snackBarType = SnackBarType.ERROR)
            }
            return false
        }
        else if (_uiState.value.projectColor.isNullOrEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Insert Project Color", snackBarType = SnackBarType.ERROR)
            }
            return false
        }
        else if (!update && _uiState.value.templateId==null){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Select Template", snackBarType = SnackBarType.ERROR)
            }
            return false
        }
        else{
            return true
        }
    }
    //..............................................................................................



    //Project Entity
    //..............................................................................................
    fun insertProjectEntity() : Boolean{
        if (!validationProjectEntity()) return false

        Timber.d(_uiState.value.projectName)
        Timber.d(_uiState.value.projectColor)
        val projectsEntity = ProjectsEntity(
            name = _uiState.value.projectName?:"",
            projectAccess = _uiState.value.projectAccessValue,
            color = _uiState.value.projectColor,
            templateId = _uiState.value.templateId
        )

        viewModelScope.launch{
            projectOfflineRepository.insertProject(projectsEntity)
            clearData()
        }

        return true
    }

    fun updateProjectEntity(projectsEntity : ProjectsEntity) : Boolean{
        if (!validationProjectEntity(true)) return false

        Timber.d(_uiState.value.projectName)
        Timber.d(_uiState.value.projectColor)

        val projectsUpdatedEntity = projectsEntity.copy(
            name = _uiState.value.projectName?:projectsEntity.name,
            projectAccess = _uiState.value.projectAccessValue,
            projectAccessName = _uiState.value.projectAccessValue.name(null),
            color = _uiState.value.projectColor?:projectsEntity.color,
            templateId = _uiState.value.templateId?:projectsEntity.templateId
        )

        viewModelScope.launch(Dispatchers.IO){
            projectOfflineRepository.updateProject(projectsUpdatedEntity)
            clearData()
        }

        return true
    }

    fun deleteProjectEntity(projectsEntity: ProjectsEntity?){
        if (projectsEntity!=null){
            viewModelScope.launch{
                projectOfflineRepository.deleteProject(projectsEntity)
            }
        }
        else{
            viewModelScope.launch{
                SnackBarManager.showSnackBar(message = "Unexpected Error : Empty item", snackBarType = SnackBarType.ERROR)
            }
        }
        clearData()
    }

    fun updateTemplateId(id:Int?) {
        _uiState.update {
            it.copy(templateId = id)
        }
        Timber.d(_uiState.value.templateId.toString())
    }
    //..............................................................................................








    //Clear Data
    //..............................................................................................
    fun clearData(projectUiState: ProjectUiState = ProjectUiState()){
        _uiState.value = projectUiState
        Timber.d("clearData")
    }
    //..............................................................................................





}