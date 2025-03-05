package com.example.data.repository.project_management.templates

import com.example.data.di.AppDispatcher
import com.example.data.di.Dispatcher
import com.example.database.dao.ProjectManagementDao
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class OfflineTemplatesStatusRepository @Inject constructor(
    private val projectManagementDao: ProjectManagementDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
): TemplatesStatusRepository {


    override suspend fun insertTemplateStatus(templateStatus: TemplatesStatusEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertTemplateStatus(templateStatus)
        }
    }

    override suspend fun insertTemplatesStatus(templatesStatus: List<TemplatesStatusEntity>) {
        withContext(ioDispatcher){
            projectManagementDao.insertTemplatesStatus(templatesStatus)
        }
    }

    override suspend fun updateTemplateStatus(templateStatus: TemplatesStatusEntity) {
        withContext(ioDispatcher){
            projectManagementDao.updateTemplateStatus(templateStatus)
        }
    }

    override suspend fun updateTemplateStatuses(templatesStatus: List<TemplatesStatusEntity>) {
        withContext(ioDispatcher){
            projectManagementDao.updateTemplateStatuses(templatesStatus)
        }
    }


    override suspend fun insertOrReplaceTemplateStatus(templateStatus: TemplatesStatusEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertOrReplaceTemplateStatus(templateStatus)
        }
    }

    override suspend fun deleteTemplateStatus(templateStatus: TemplatesStatusEntity) {
        withContext(ioDispatcher){
            projectManagementDao.deleteTemplateStatus(templateStatus)
        }
    }

    override fun templateStatusList(): Flow<List<TemplatesStatusEntity>> = projectManagementDao.templatesStatusList().flowOn(ioDispatcher)

    override fun countTemplatesStatus(): Flow<Int> = projectManagementDao.countTemplatesStatus().flowOn(ioDispatcher)

    override fun countTemplatesStatusInTemplateId(templateId:Int): Flow<Int> = projectManagementDao.countTemplatesStatusInTemplateId(templateId).flowOn(ioDispatcher)
    override fun getMaxOrder(templateId: Int): Flow<Int?> = projectManagementDao.getMaxOrder(templateId).flowOn(ioDispatcher)


}