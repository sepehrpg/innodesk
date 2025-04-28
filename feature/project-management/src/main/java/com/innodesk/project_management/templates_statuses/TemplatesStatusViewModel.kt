package com.innodesk.project_management.templates_statuses

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.qualifier.OfflineRepository
import com.example.data.repository.project_management.templates.TemplatesRepository
import com.example.data.repository.project_management.templates.TemplatesStatusRepository
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import com.example.designsystem.component.SnackBarManager
import com.example.designsystem.component.SnackBarType
import com.example.designsystem.extension.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



data class TemplateStatusUiState(
    val templateId: Int? = null,
    val templateName:String? = null,
    var tempTemplateStatusList: List<TemplatesStatusEntity> = emptyList(),

    val templateStatusColor:String? = null,
    val templateStatusName:String? = null,

)




@HiltViewModel
class TemplatesStatusViewModel @Inject constructor(
    @OfflineRepository private val templateOfflineRepository: TemplatesRepository,
    @OfflineRepository private val templateStatusOfflineRepository: TemplatesStatusRepository
): ViewModel(){

    private val _uiState:MutableStateFlow<TemplateStatusUiState> = MutableStateFlow(TemplateStatusUiState())
    val uiState:StateFlow<TemplateStatusUiState> = _uiState.asStateFlow()

    val templateList: Flow<List<TemplatesEntity>> = templateOfflineRepository.templatesList()
    //var templateWithStatusList: Flow<TemplateWithStatuses?> = flow { emit(null) }


    //Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    // Get List
    //..............................................................................................
    var isDataLoaded = false
    fun getTemplateWithStatus(templateId:Int){
        Timber.tag("HXZJHCGXZHGCVHA").d("$templateId -- $isDataLoaded")
        viewModelScope.launch {
            templateOfflineRepository.getTemplateWithStatus(templateId).collect { data ->
                if (!isDataLoaded && data?.statuses != null) {
                    _uiState.value = _uiState.value.copy(
                        tempTemplateStatusList = data.statuses.sortedBy { it.order }
                    )
                    Timber.tag("HXZJHCGXZHGCVHA").d("PASS")
                    isDataLoaded = true
                }
            }
        }
    }
    //..............................................................................................



    fun updateTemplateName(name: String) {
        _uiState.update {
            it.copy(templateName = name)
        }
        Timber.d(_uiState.value.templateName)
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



    private fun validationTemplateEntity():Boolean {
        if (_uiState.value.tempTemplateStatusList.isEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Empty Template Status : Please Add Status", snackBarType = SnackBarType.ERROR)
            }
            return false
        }
        if (_uiState.value.templateName.isNullOrEmpty()){
            viewModelScope.launch {
                SnackBarManager.showSnackBar("Insert Template Name", snackBarType = SnackBarType.ERROR)
            }
            return false
        }

        return true
    }

    private fun validationTemplateStatusEntity():Boolean {

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

        return true
    }


    // Template Entity
    //..............................................................................................
    fun insertTemplateWithStatuses() : Boolean {

        if (!validationTemplateEntity()) return false

        //Set Order
        _uiState.value.tempTemplateStatusList.mapIndexed { index, templatesStatusEntity ->
            templatesStatusEntity.order = index
        }
        //Just Log
        _uiState.value.tempTemplateStatusList.forEachIndexed { index, templatesStatusEntity ->
            Timber.d("Index: $index , item:$templatesStatusEntity")
        }

        val template = TemplatesEntity(
            name = _uiState.value.templateName?:""
        )
        Timber.d("template: $template ")
        viewModelScope.launch {
            templateOfflineRepository.insertTemplateWithStatuses(template,_uiState.value.tempTemplateStatusList)
            clearTemplate()
        }

        return true
    }

    fun updateTemplatesWithStatusList(selectedItem : TemplatesEntity?): Boolean {
        if (!validationTemplateEntity()) return false


        _uiState.value.tempTemplateStatusList.mapIndexed { index, templatesStatusEntity ->
            templatesStatusEntity.order = index
        }
        //Just Log
        _uiState.value.tempTemplateStatusList.forEachIndexed { index, templatesStatusEntity ->
            Timber.d("Index: $index , item:$templatesStatusEntity")
        }

        if (selectedItem!=null){
            val templateEntity =  selectedItem.copy(
                name = _uiState.value.templateName?:""
            )
            viewModelScope.launch{


                //templateStatusOfflineRepository.deleteTemplateStatusWithTemplateId(selectedItem.id)

                /*val updatedStatusList = _uiState.value.tempTemplateStatusList.map {
                    it.copy(templateId = selectedItem.id)
                }*/
                //templateStatusOfflineRepository.insertTemplatesStatus(updatedStatusList)

                Timber.d(_uiState.value.tempTemplateStatusList.toString())
                templateOfflineRepository.updateTemplateWithStatuses(templateEntity,_uiState.value.tempTemplateStatusList)

                //templateOfflineRepository.updateTemplateWithStatuses(templateEntity,_uiState.value.tempTemplateStatusList)
                //clearTemplate()
            }
            return true
        }
        else{
            viewModelScope.launch{
                SnackBarManager.showSnackBar(message = "Unexpected Error : Empty item", snackBarType = SnackBarType.ERROR)
            }
            return false
        }

    }

    fun deleteTemplate(selectedItem: TemplatesEntity?){
        //if (!validationInsertProjectEntity()) return

        if (selectedItem!=null){
            viewModelScope.launch{
                templateOfflineRepository.deleteTemplate(selectedItem)
                clearTemplate()
            }
        }
        else{
            viewModelScope.launch{
                SnackBarManager.showSnackBar(message = "Unexpected Error : Empty item", snackBarType = SnackBarType.ERROR)
            }
        }
    }
    //..............................................................................................




    //Template Status Entity
    //..............................................................................................
    //Local List
    fun insertTempTemplateStatus() : Boolean {
        if (!validationTemplateStatusEntity()) return false

        val maxOrder:Int = _uiState.value.tempTemplateStatusList.maxOfOrNull { it.order }?:0
        val templateStatusEntity = TemplatesStatusEntity(
            name = _uiState.value.templateStatusName?:"",
            color = _uiState.value.templateStatusColor,
            order = if(_uiState.value.tempTemplateStatusList.isNotEmpty()) maxOrder + 1 else 0
        )
        _uiState.value = _uiState.value.copy(
            tempTemplateStatusList = _uiState.value.tempTemplateStatusList + templateStatusEntity
        )

        Timber.d(_uiState.value.tempTemplateStatusList.toString())
        Timber.d(maxOrder.toString())

        clearTemplateStatus()

        return true
    }

    //Local List
    fun updateTempTemplateStatus(updatedStatus: TemplatesStatusEntity) : Boolean {
        if (!validationTemplateStatusEntity()) return false

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

        clearTemplateStatus()

        return true
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
    //..............................................................................................



    fun clearData(templateStatusUiState: TemplateStatusUiState = TemplateStatusUiState()){
        _uiState.value = templateStatusUiState
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
                templateId = null,
                templateName = null,
                tempTemplateStatusList = mutableListOf(),
            )
        }
        clearTemplateStatus()
        Timber.d("clearUpsertTemplate")
        //Timber.tag("HXZJHCGXZHGCVHA").d("clearTemplateStatus")

    }
    fun clearTemplate(){
        //clear selected and searched for template

        clearUpsertTemplate()
        Timber.d("clearProject")
    }


    fun onMove(toIndex:Int,fromIndex:Int){
        _uiState.update { it.copy(
            tempTemplateStatusList = _uiState.value.tempTemplateStatusList.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        ) }

        Timber.d("From Index:$toIndex -- To Index::$toIndex")
    }

}