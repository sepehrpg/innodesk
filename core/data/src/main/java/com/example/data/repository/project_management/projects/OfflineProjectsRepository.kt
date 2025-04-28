package com.example.data.repository.project_management.projects

import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.database.dao.ProjectsManagementDao
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject



class OfflineProjectsRepository @Inject constructor(
    private val projectManagementDao: ProjectsManagementDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
): ProjectsRepository {
    override suspend fun insertProject(project: ProjectsEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertProject(project)
        }
    }

    override suspend fun insertProjects(projects: List<ProjectsEntity>) {
        withContext(ioDispatcher){
            projectManagementDao.insertProjects(projects)
        }
    }

    override fun getProjectWithTemplateAndStatuses(projectId: Int): Flow<ProjectWithTemplateAndStatuses?> =
        projectManagementDao.getProjectWithTemplateAndStatuses(projectId).flowOn(ioDispatcher)

    override suspend fun updateProject(project: ProjectsEntity) {
        withContext(ioDispatcher){
            Timber.tag("REPOSITORY").d(project.toString())
            projectManagementDao.updateProject(project)
        }
    }

    override suspend fun insertOrReplaceProject(project: ProjectsEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertOrReplaceProject(project)
        }
    }

    override suspend fun deleteProject(project: ProjectsEntity) {
        withContext(ioDispatcher){
            projectManagementDao.deleteProject(project)
        }
    }

    override fun projectsList(): Flow<List<ProjectsEntity>> = projectManagementDao.projectsList().flowOn(ioDispatcher)

    override fun countProjects(): Flow<Int> = projectManagementDao.countProjects().flowOn(ioDispatcher)


}