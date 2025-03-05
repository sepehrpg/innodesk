package com.example.data.repository.project_management.templates

import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow



interface TemplatesStatusRepository {
    suspend fun insertTemplateStatus(templateStatus: TemplatesStatusEntity)

    suspend fun insertTemplatesStatus(templatesStatus: List<TemplatesStatusEntity>)

    suspend fun updateTemplateStatus(templateStatus: TemplatesStatusEntity)

    suspend fun updateTemplateStatuses(templatesStatus: List<TemplatesStatusEntity>)


    suspend fun insertOrReplaceTemplateStatus(templateStatus: TemplatesStatusEntity)

    suspend fun deleteTemplateStatus(templateStatus: TemplatesStatusEntity)

    fun templateStatusList(): Flow<List<TemplatesStatusEntity>>

    fun countTemplatesStatus(): Flow<Int>
    fun countTemplatesStatusInTemplateId(templateId:Int): Flow<Int>

     fun getMaxOrder(templateId:Int): Flow<Int?>
}