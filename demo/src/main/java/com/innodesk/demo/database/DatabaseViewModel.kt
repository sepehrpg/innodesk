package com.innodesk.demo.database


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.OfflineRepository
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.database.model.pm.project.ProjectsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DatabaseViewModel @Inject constructor(
    @OfflineRepository private val projectsRepository: ProjectsRepository
):ViewModel(){

    val projectsFlow: Flow<List<ProjectsEntity>> = projectsRepository.projectsList()
    /*val projectsStateFlow: StateFlow<List<ProjectsEntity>> =
        projectsRepository.projectsList()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )*/

    init {
        viewModelScope.launch {
            projectsRepository.countProjects22().collect{
                Timber.d(it.toString())
            }

            projectsFlow.collect{
                Timber.d(it.toString())
            }
        }
    }


    fun insertProject(projectsEntity: ProjectsEntity){
        viewModelScope.launch{
            projectsRepository.insertProject(projectsEntity)
            Timber.d("Current thread: ${Thread.currentThread().name}")
        }
    }

    fun deleteProject(projectsEntity: ProjectsEntity){
        viewModelScope.launch{
            projectsRepository.deleteProject(projectsEntity)
            Timber.d("Current thread: ${Thread.currentThread().name}")
        }
    }

    fun updateProject(projectsEntity: ProjectsEntity){
        viewModelScope.launch{
            projectsRepository.updateProject(projectsEntity)
            Timber.d("Current thread: ${Thread.currentThread().name}")
        }
    }
}


