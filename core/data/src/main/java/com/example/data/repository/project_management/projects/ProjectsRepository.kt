package com.example.data.repository.project_management.projects

import com.example.database.model.pm.project.ProjectsEntity
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    suspend fun insertProject(project: ProjectsEntity)

    suspend fun insertProjects(projects: List<ProjectsEntity>)

    suspend fun updateProject(project: ProjectsEntity)

    suspend fun insertOrReplaceProject(project: ProjectsEntity)

    suspend fun deleteProject(project: ProjectsEntity)

    fun projectsList(): Flow<List<ProjectsEntity>>

    fun countProjects(): Flow<Int>

    fun countProjects22(): Flow<Int>
}

