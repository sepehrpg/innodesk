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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
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
    var tempTemplateStatusList: List<TemplatesStatusEntity> = emptyList(),

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
    //var templateWithStatusList: Flow<TemplateWithStatuses?> = flow { emit(null) }


    // Get List
    //..............................................................................................
    private var isDataLoaded = false
    fun getTemplateWithStatus(templateId:Int){

        /*templateWithStatusList = templateOfflineRepository.templateWithStatusList(templateId).map { templateWithStatuses ->
            templateWithStatuses?.copy(
                statuses = templateWithStatuses.statuses.sortedBy { it.order }
            )
        }*/

        viewModelScope.launch {
            templateOfflineRepository.templateWithStatusList(templateId).collect { data ->
                if (data?.statuses != null){
                    _uiState.value = _uiState.value.copy(
                        tempTemplateStatusList = data.statuses.sortedBy { it.order }
                    )
                }
            }

            /*val dbList = templateOfflineRepository.templateWithStatusList(templateId).firstOrNull()?.statuses?.sortedBy { it.order }
            if(dbList!=null){
                _uiState.value = _uiState.value.copy(
                    tempTemplateStatusList = dbList
                )
            }*/

            /*templateOfflineRepository.templateWithStatusList(templateId).collect { data ->
                if (!isDataLoaded && data?.statuses != null) {
                    _uiState.value = _uiState.value.copy(
                        tempTemplateStatusList = data.statuses.sortedBy { it.order }
                    )
                    isDataLoaded = true
                }
            }*/

        }
    }
    //..............................................................................................




   //Update Ui State
    //..............................................................................................
    fun updateProjectName(name: String) {
        _uiState.update {
            it.copy(projectName = name)
        }
       Timber.d(_uiState.value.projectName)
   }

    fun updateTemplateName(name: String) {
        _uiState.update {
            it.copy(templateName = name)
        }
        Timber.d(_uiState.value.templateName)
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

    fun updateTemplateStatusName(name: String) {
        _uiState.update {
            it.copy(templateStatusName = name)
        }
        Timber.d(_uiState.value.templateStatusName)
    }

    fun updateTemplateStatusColor(color: Color) {
        _uiState.update { it.copy(templateStatusColor = color.toHexString()) }
        Timber.d(_uiState.value.templateStatusColor)
    }
    //..............................................................................................


    //Validation
    private fun validationInsertProjectEntity():Boolean{

        return true
    }
    //..............................................................................................



    //Project Entity
    //..............................................................................................
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
            clearProject()
        }
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
            clearProject()
        }
    }

    fun deleteProjectEntity(projectsEntity: ProjectsEntity?){
        if (projectsEntity!=null){
            viewModelScope.launch{
                projectOfflineRepository.deleteProject(projectsEntity)
            }
        }
        clearProject()
    }
    //..............................................................................................


    // Template Entity
    //..............................................................................................
    fun insertTemplate(){

        //if (!validationInsertProjectEntity()) return

        //Set Order
        _uiState.value.tempTemplateStatusList.mapIndexed { index, templatesStatusEntity ->
            templatesStatusEntity.order = index
        }
        _uiState.value.tempTemplateStatusList.forEachIndexed { index, templatesStatusEntity ->
            Timber.d("Index: $index , item:$templatesStatusEntity")
        }

        val template = TemplatesEntity(
            name = _uiState.value.templateName?:"NULL"
        )
        Timber.d("template: $template ")
        viewModelScope.launch {
            templateOfflineRepository.insertTemplateWithStatuses(template,_uiState.value.tempTemplateStatusList)
            clearTemplate()
        }
    }

    fun deleteTemplate(selectedItem: TemplatesEntity?){
        //if (!validationInsertProjectEntity()) return
        selectedItem?.let {
            viewModelScope.launch{
                templateOfflineRepository.deleteTemplate(selectedItem)
                clearTemplate()
            }
        }
    }

    fun updateTemplatesWithStatusList(selectedItem : TemplatesEntity?){

        _uiState.value.tempTemplateStatusList.mapIndexed { index, templatesStatusEntity ->
            templatesStatusEntity.order = index
        }
        _uiState.value.tempTemplateStatusList.forEachIndexed { index, templatesStatusEntity ->
            Timber.d("Index: $index , item:$templatesStatusEntity")
        }

        selectedItem?.let {
            val templateEntity =  selectedItem.copy(
                name = _uiState.value.templateName?:""
            )
            viewModelScope.launch{
                _uiState.value.tempTemplateStatusList.forEach {
                    if (it.id!=0){
                        templateStatusOfflineRepository.updateTemplateStatus(it)
                    }
                    else{
                        templateStatusOfflineRepository.insertTemplateStatus(it.copy(
                            templateId = selectedItem.id
                        ))
                    }
                }
                Timber.d(_uiState.value.tempTemplateStatusList.toString())
                templateOfflineRepository.updateTemplate(templateEntity)

                //templateOfflineRepository.updateTemplateWithStatuses(templateEntity,_uiState.value.tempTemplateStatusList)
                clearTemplate()
            }
        }
    }
    //..............................................................................................


    //Template Status Entity
    //..............................................................................................
    fun insertTemplateStatusEntity(selectedItem: TemplatesEntity){
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
                clearTemplateStatus()
            }
        }
    }

    //Local List
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
        //_uiState.value.tempTemplateStatusList.add(templateStatusEntity)

        Timber.d(_uiState.value.tempTemplateStatusList.toString())
        Timber.d(maxOrder.toString())

        clearTemplateStatus()
        //clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }

    //Local List
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

        /*_uiState.value.tempTemplateStatusList.replaceAll {
            status ->
            if (status.order == item.order) item else status
        }*/
        /*_uiState.value.tempTemplateStatusList.map { status ->
            if (status.order == item.order) item else status
        }*/

        Timber.d(item.toString())
        Timber.d(updatedStatus.toString())
        Timber.d(_uiState.value.tempTemplateStatusList.toString())

        clearTemplateStatus()
        //clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }

    //Local List
    fun deleteTempTemplateStatus(updatedStatus: TemplatesStatusEntity?){
        updatedStatus?.let {

            _uiState.value = _uiState.value.copy(
                tempTemplateStatusList = _uiState.value.tempTemplateStatusList.filterNot { it.order == updatedStatus.order }
            )
            //_uiState.value.tempTemplateStatusList.filterNot { it.order == updatedStatus.order }
            //_uiState.value.tempTemplateStatusList.removeIf { it.order == updatedStatus.order }
            Timber.d(updatedStatus.toString())
            Timber.d(updatedStatus.toString())
            Timber.d(_uiState.value.tempTemplateStatusList.toString())
            Timber.d(_uiState.value.tempTemplateStatusList.size.toString())
        }

        clearTemplateStatus()
        //clearData(ProjectUiState(tempTemplateStatusList = _uiState.value.tempTemplateStatusList))
    }

    fun deleteTemplateStatusEntity(templateStatusEntity:TemplatesStatusEntity?){
        deleteTempTemplateStatus(templateStatusEntity)
        templateStatusEntity?.let {
            viewModelScope.launch{
                templateStatusOfflineRepository.deleteTemplateStatus(templateStatusEntity)
            }
        }
        clearTemplateStatus()
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
            updateTempTemplateStatus(templateStatusUpdatedEntity)

            viewModelScope.launch(Dispatchers.IO){
                templateStatusOfflineRepository.updateTemplateStatus(templateStatusUpdatedEntity)
                clearTemplateStatus()
            }
        }

    }
    //..............................................................................................





    //Clear Data
    //..............................................................................................
    private fun clearData(projectUiState: ProjectUiState = ProjectUiState()){
        _uiState.value = projectUiState
        Timber.d("clearData")
    }

    fun clearTemplateStatus(){
        _uiState.update {
            it.copy(
                templateStatusColor = null,
                templateStatusName = null,
            )
        }
        Timber.d("clearTemplateStatus")
    }
    fun clearUpsertTemplate(){
        _uiState.update {
            it.copy(
                templateName = null,
                tempTemplateStatusList = mutableListOf(),
            )
        }
        isDataLoaded = false
        clearTemplateStatus()
        Timber.d("clearUpsertTemplate")
    }
    fun clearTemplate(){
        //clear selected and searched for template

        clearUpsertTemplate()
        Timber.d("clearProject")
    }
    fun clearProject(){
        clearData()
        Timber.d("clearProject")
    }
    //..............................................................................................



    fun onMove(toIndex:Int,fromIndex:Int){
        _uiState.update { it.copy(
            tempTemplateStatusList = _uiState.value.tempTemplateStatusList.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        ) }
    }

}