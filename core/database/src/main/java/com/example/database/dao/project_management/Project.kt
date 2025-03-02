package com.example.database.dao.project_management

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates.TemplatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Project {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectsEntity>)

    @Update
    suspend fun updateProject(project: ProjectsEntity)

    @Upsert
    suspend fun insertOrReplaceProject(project: ProjectsEntity)

    @Delete
    fun deleteProject(project: ProjectsEntity)

    @Query("SELECT * FROM projects")
    fun projectsList(): Flow<List<ProjectsEntity>>

    @Query("SELECT count(*) FROM projects")
    fun countProjects(): Flow<Int>
}