package com.innodesk.project_management.projects

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.OfflineRepository
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.data.repository.project_management.templates.TemplatesRepository
import com.example.data.repository.project_management.templates.TemplatesStatusRepository
import com.example.database.model.pm.project.ProjectAccess
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates.TemplateWithStatuses
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import com.example.designsystem.extension.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class ProjectUiState(
    val message:String = "",
    val status:String = "",

    val projectName:String? = null,
    val projectAccessValue:ProjectAccess = ProjectAccess.PRIVATE,
    val projectColor:String? = null,

    val templateName:String? = null,
    val tempTemplateStatusList: List<TemplatesStatusEntity> = emptyList(),

    val templateStatusColor:String? = null,
    val templateStatusName:String? = null,

    )


@HiltViewModel
class ProjectsViewModel @Inject constructor(
    @OfflineRepository private val projectOfflineRepository: ProjectsRepository,
    @OfflineRepository private val templateOfflineRepository: TemplatesRepository,
    @OfflineRepository private val templateStatusOfflineRepository: TemplatesStatusRepository
): ViewModel(){

    private val _uiState:MutableStateFlow<ProjectUiState> = MutableStateFlow(ProjectUiState())
    val uiState:StateFlow<ProjectUiState> = _uiState.asStateFlow()

    val projectsList: Flow<List<ProjectsEntity>> = projectOfflineRepository.projectsList()
    val templateList: Flow<List<TemplatesEntity>> = templateOfflineRepository.templateList()
    var templateWithStatusList: Flow<TemplateWithStatuses?> = flow { emit(null) }


    fun getTemplateWithStatus(templateId:Int){
        templateWithStatusList = templateOfflineRepository.templateWithStatusList(templateId).map { templateWithStatuses ->
            templateWithStatuses.copy(
                statuses = templateWithStatuses.statuses.sortedBy { it.order }
            )
        }
    }


    fun updateProjectName(name: String) {
        _uiState.update {
            it.copy(projectName = name)
        }
    }
    fun updateTemplateName(name: String) {
        _uiState.update {
            it.copy(templateName = name)
        }
    }
    fun updateProjectAccessId(projectAccessValue: ProjectAccess) {
        Timber.d(projectAccessValue.toString())
        _uiState.update { it.copy(projectAccessValue = projectAccessValue) }
        Timber.d(_uiState.value.projectAccessValue.toString())

    }
    fun updateProjectColor(color: Color) {
        _uiState.update { it.copy(projectColor = color.toHexString()) }
    }


    fun updateTemplateStatusName(name: String) {
        _uiState.update {
            it.copy(templateStatusName = name)
        }
    }

    fun updateTemplateStatusColor(color: Color) {
        _uiState.update { it.copy(templateStatusColor = color.toHexString()) }
    }

    fun updateProjectEntity(projectsEntity : ProjectsEntity){
        //if (!validationInsertProjectEntity()) return

        Timber.d(_uiState.value.projectName)
        Timber.d(_uiState.value.projectColor)

        val projectsUpdatedEntity = projectsEntity.copy(
            name = _uiState.value.projectName?:projectsEntity.name,
            projectAccess = _uiState.value.projectAccessValue,
            projectAccessName = _uiState.value.projectAccessValue.name(null),
            color = _uiState.value.projectColor?:projectsEntity.color
        )

        viewModelScope.launch(Dispatchers.IO){
            projectOfflineRepository.updateProject(projectsUpdatedEntity)
            clearData()
        }
    }



    private fun validationInsertProjectEntity():Boolean{

        return true
    }

    fun insertProjectEntity(){

        //if (!validationInsertProjectEntity()) return
        Timber.d(_uiState.value.projectName)
        Timber.d(_uiState.value.projectColor)
        val projectsEntity = ProjectsEntity(
            name = _uiState.value.projectName?:"",
            projectAccess = _uiState.value.projectAccessValue,
            color = _uiState.value.projectColor
        )

        viewModelScope.launch{
            projectOfflineRepository.insertProject(projectsEntity)
            clearData()
        }
    }




    fun insertTemplateStatusEntityInTemplateUpdated(selectedItem: TemplatesEntity){
        //if (!validationInsertProjectEntity()) return

        Timber.d(_uiState.value.templateStatusName)
        Timber.d(_uiState.value.templateStatusColor)

        selectedItem?.let {
            viewModelScope.launch{
                val maxOrder = templateStatusOfflineRepository.getMaxOrder(selectedItem.id).first()?:0
                val templateStatusEntity = TemplatesStatusEntity(
                    name = _uiState.value.templateStatusName?:"",
                    color = _uiState.value.templateStatusColor,
                    order = if(maxOrder!=0) maxOrder + 1 else maxOrder,
                    templateId = it.id
                )
                templateStatusOfflineRepository.insertTemplateStatus(templateStatusEntity)
                clearData()
            }
        }
    }





    fun deleteProjectEntity(projectsEntity: ProjectsEntity?){
        if (projectsEntity!=null){
            viewModelScope.launch{
                projectOfflineRepository.deleteProject(projectsEntity)
            }
        }
        clearData()
    }

    fun deleteTemplateStatusEntity(templateStatusEntity:TemplatesStatusEntity?){
        templateStatusEntity?.let {
            viewModelScope.launch{
                templateStatusOfflineRepository.deleteTemplateStatus(templateStatusEntity)
            }
        }
        clearData()
    }



    fun updateTemplateStatusEntity(templateStatusEntity:TemplatesStatusEntity?){
        //if (!validationInsertProjectEntity()) return

        Timber.d(_uiState.value.templateStatusName)
        Timber.d(_uiState.value.templateStatusColor)

        templateStatusEntity?.let {
            val templateStatusUpdatedEntity = it.copy(
                name = _uiState.value.templateStatusName?:it.name,
                color = _uiState.value.templateStatusColor?:it.color
            )

            viewModelScope.launch(Dispatchers.IO){
                templateStatusOfflineRepository.updateTemplateStatus(templateStatusUpdatedEntity)
                clearData()
            }
        }

    }
    //..............................................................................................




    //..............................................................................................
    fun insertTempTemplateStatus(){

        //if (!validationInsertProjectEntity()) return

        val maxOrder:Int = _uiState.value.tempTemplateStatusList.maxOfOrNull { it.order }?:0
        val templateStatusEntity = TemplatesStatusEntity(
            name = _uiState.value.templateStatusName?:"",
            color = _uiState.value.templateStatusColor,
            order = if(_uiState.value.tempTemplateStatusList.isNotEmpty()) maxOrder + 1 else 0
            //order = _uiState.value.tempTemplateStatusList.size
        )
        _uiState.value = _uiState.value.copy(
            tempTemplateStatusList = _uiState.value.tempTemplateStatusList + templateStatusEntity
        )

        Timber.d(_uiState.value.tempTemplateStatusList.toString())
        Timber.d(maxOrder.toString())

        clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }
    fun updateTempTemplateStatus(updatedStatus: TemplatesStatusEntity) {

        val item = TemplatesStatusEntity(
            name = _uiState.value.templateStatusName?:updatedStatus.name,
            color = _uiState.value.templateStatusColor?:updatedStatus.color,
            order = updatedStatus.order
        )

        _uiState.value = _uiState.value.copy(
            tempTemplateStatusList = _uiState.value.tempTemplateStatusList.map { status ->
                if (status.order == item.order) item else status
            }
        )
        Timber.d(item.toString())
        Timber.d(updatedStatus.toString())
        Timber.d(_uiState.value.tempTemplateStatusList.toString())

        clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }

    fun deleteTempTemplateStatus(updatedStatus: TemplatesStatusEntity?){
        updatedStatus?.let {
            _uiState.value = _uiState.value.copy(
                tempTemplateStatusList = _uiState.value.tempTemplateStatusList.filterNot { it.order == updatedStatus.order }
            )
        }
        Timber.d(updatedStatus.toString())
        Timber.d(_uiState.value.tempTemplateStatusList.toString())

        clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }

    fun clearTempTemplateStatus(){
        _uiState.value = _uiState.value.copy(
            tempTemplateStatusList = emptyList()
        )

        clearData()
    }
    //..............................................................................................




    //..............................................................................................
    private fun clearData(projectUiState: ProjectUiState = ProjectUiState()){
        _uiState.value = projectUiState
    }

    fun clearTemplateStatus(){

    }
    fun clearTemplate(){

    }
    fun clearProject(){

    }
    //..............................................................................................



}