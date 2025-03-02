package com.example.data.repository.project_management.templates

import com.example.data.di.AppDispatcher
import com.example.data.di.Dispatcher
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.database.dao.ProjectManagementDao
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates.TemplateWithStatuses
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject




class OfflineTemplatesRepository @Inject constructor(
    private val projectManagementDao: ProjectManagementDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
): TemplatesRepository {

    override suspend fun insertTemplate(template: TemplatesEntity) {
        withContext(ioDispatcher){
            Timber.d("Call : insertTemplate")
            projectManagementDao.insertTemplate(template)
        }
    }

    override suspend fun insertTemplates(templates: List<TemplatesEntity>) {
        withContext(ioDispatcher){
            Timber.d("Call : insertTemplates")
            projectManagementDao.insertTemplates(templates)
        }
    }

    override suspend fun updateTemplate(template: TemplatesEntity) {
        withContext(ioDispatcher){
            Timber.d("Call : updateTemplate")
            projectManagementDao.updateTemplate(template)
        }
    }

    override suspend fun insertOrReplaceTemplate(template: TemplatesEntity) {
        withContext(ioDispatcher){
            Timber.d("Call : insertOrReplaceTemplate")
            projectManagementDao.insertOrReplaceTemplate(template)
        }
    }

    override suspend fun deleteTemplate(template: TemplatesEntity) {
        withContext(ioDispatcher){
            Timber.d("Call : deleteTemplate")
            projectManagementDao.deleteTemplate(template)
        }
    }

    override fun templateList(): Flow<List<TemplatesEntity>> {
        return projectManagementDao.templatesList()
            .onStart { Timber.d("Call Flow : templateList") }
            .onEach { Timber.d("On Each Call : templateList") }
            .flowOn(ioDispatcher)
    }

    override fun countTemplates(): Flow<Int> = projectManagementDao.countTemplates().flowOn(ioDispatcher)

    override suspend fun insertTemplateWithStatuses(
        template: TemplatesEntity,
        statuses: List<TemplatesStatusEntity>
    ) {
        withContext(ioDispatcher){
            Timber.d("Call : insertTemplateWithStatuses")
            projectManagementDao.insertTemplateWithStatuses(template,statuses)
        }
    }

    override fun templateWithStatusList(templateId: Int): Flow<TemplateWithStatuses> {
        return projectManagementDao.templateWithStatusList(templateId)
            .onStart { Timber.d("Call Flow : templateWithStatusList") }
            .onEach { Timber.d("On Each Call : templateWithStatusList") }
            .flowOn(ioDispatcher)
    }


}