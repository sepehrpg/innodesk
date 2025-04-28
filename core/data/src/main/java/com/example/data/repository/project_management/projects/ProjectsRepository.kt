package com.example.data.repository.project_management.projects

import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    suspend fun insertProject(project: ProjectsEntity)

    suspend fun insertProjects(projects: List<ProjectsEntity>)

    fun getProjectWithTemplateAndStatuses(projectId: Int): Flow<ProjectWithTemplateAndStatuses?>

    suspend fun updateProject(project: ProjectsEntity)

    suspend fun insertOrReplaceProject(project: ProjectsEntity)

    suspend fun deleteProject(project: ProjectsEntity)

    fun projectsList(): Flow<List<ProjectsEntity>>

    fun countProjects(): Flow<Int>

}

