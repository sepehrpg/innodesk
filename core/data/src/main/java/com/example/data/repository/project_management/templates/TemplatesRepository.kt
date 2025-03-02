package com.example.data.repository.project_management.templates

import androidx.room.Query
import androidx.room.Transaction
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates.TemplateWithStatuses
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow


interface TemplatesRepository {
    suspend fun insertTemplate(template: TemplatesEntity)

    suspend fun insertTemplates(templates: List<TemplatesEntity>)

    suspend fun updateTemplate(template: TemplatesEntity)

    suspend fun insertOrReplaceTemplate(template: TemplatesEntity)

    suspend fun deleteTemplate(template: TemplatesEntity)

    fun templateList(): Flow<List<TemplatesEntity>>

    fun countTemplates(): Flow<Int>


    suspend fun insertTemplateWithStatuses(template: TemplatesEntity, statuses: List<TemplatesStatusEntity>)

    fun templateWithStatusList(templateId: Int): Flow<TemplateWithStatuses>
}