package com.example.database.dao.project_management

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow

interface TemplatesStatus {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplatesStatus(templatesStatus: List<TemplatesStatusEntity>)

    @Update
    suspend fun updateTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Update
    suspend fun updateTemplateStatuses(templatesStatus: List<TemplatesStatusEntity>)

    @Upsert
    suspend fun insertOrReplaceTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Query("SELECT * FROM templates_status")
    fun templatesStatusList(): Flow<List<TemplatesStatusEntity>>

    @Delete
    fun deleteTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Query("SELECT count(*) FROM templates_status")
    fun countTemplatesStatus(): Flow<Int>

    @Query("SELECT count(*) FROM templates_status WHERE template_id=:templateId")
    fun countTemplatesStatusInTemplateId(templateId:Int): Flow<Int>

    @Query("SELECT MAX(`order`) FROM templates_status WHERE template_id=:templateId")
    fun getMaxOrder(templateId:Int): Flow<Int?>





}