package com.example.data.repository.project_management.templates

import com.example.database.model.pm.templates_statuses.relationships.TemplateWithStatuses
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow


interface TemplatesRepository {
    suspend fun insertTemplate(template: TemplatesEntity)

    suspend fun insertTemplates(templates: List<TemplatesEntity>)

    suspend fun updateTemplate(template: TemplatesEntity)

    suspend fun upsertTemplate(template: TemplatesEntity)

    suspend fun deleteTemplate(template: TemplatesEntity)

    fun templatesList(): Flow<List<TemplatesEntity>>

    fun countTemplates(): Flow<Int>


    suspend fun insertTemplateWithStatuses(template: TemplatesEntity, statuses: List<TemplatesStatusEntity>)
    suspend fun updateTemplateWithStatuses(template: TemplatesEntity, statuses: List<TemplatesStatusEntity>)

    fun getTemplateWithStatus(templateId: Int): Flow<TemplateWithStatuses?>
}