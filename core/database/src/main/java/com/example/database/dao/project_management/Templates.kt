package com.example.database.dao.project_management

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.templates.TemplateWithStatuses
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow


interface Templates {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(templates: TemplatesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<TemplatesEntity>)

    @Update
    suspend fun updateTemplate(templates: TemplatesEntity)

    @Upsert
    suspend fun insertOrReplaceTemplate(templates: TemplatesEntity)

    @Query("SELECT * FROM templates")
    fun templatesList(): Flow<List<TemplatesEntity>>

    @Delete
    fun deleteTemplate(templates: TemplatesEntity)

    @Query("SELECT count(*) FROM templates")
    fun countTemplates(): Flow<Int>


    @Insert
    suspend fun insertTemplateTransaction(templates: TemplatesEntity): Long

    @Transaction
    suspend fun insertTemplateWithStatuses(
        template: TemplatesEntity,
        statuses: List<TemplatesStatusEntity>
    ) {
        // First insert the template
        val templateId = insertTemplateTransaction(template)

        //deleteTemplateStatus(templateId.toInt())

        // Then update the statuses with the correct templateId
        val updatedStatuses = statuses.map { it.copy(templateId = templateId.toInt()) }

        // Insert the statuses
        insertTemplatesStatusTransaction(updatedStatuses)
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplatesStatusTransaction(templatesStatus: List<TemplatesStatusEntity>)

    @Query("DELETE FROM templates_status WHERE template_id = :templateId")
    suspend fun deleteTemplateStatus(templateId: Int)




    // Fetch template with its statuses
    @Transaction
    @Query("SELECT * FROM templates WHERE id = :templateId")
    fun templateWithStatusList(templateId: Int): Flow<TemplateWithStatuses?>?


    @Update
    suspend fun updateTemplateTransaction(templates: TemplatesEntity)

    @Transaction
    suspend fun updateTemplateWithStatuses(
        template: TemplatesEntity,
        statuses: List<TemplatesStatusEntity>
    ) {

        // First update the template
        updateTemplateTransaction(template)

        deleteTemplateStatus(template.id)

        // Then update the statuses with the correct templateId
        val updatedStatuses = statuses.map { it.copy(templateId = template.id) }

        // Insert the statuses
        insertTemplatesStatusTransaction(updatedStatuses)

    }
}


