package com.example.data.di

import com.example.data.di.qualifier.OfflineRepository
import com.example.data.repository.project_management.projects.OfflineProjectsRepository
import com.example.data.repository.project_management.projects.ProjectsRepository
import com.example.data.repository.project_management.tasks.OfflineTasksRepository
import com.example.data.repository.project_management.tasks.TasksRepository
import com.example.data.repository.project_management.templates.OfflineTemplatesRepository
import com.example.data.repository.project_management.templates.OfflineTemplatesStatusRepository
import com.example.data.repository.project_management.templates.TemplatesRepository
import com.example.data.repository.project_management.templates.TemplatesStatusRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @OfflineRepository
    internal abstract fun bindsProjectsRepository(
        projectsRepository: OfflineProjectsRepository,
    ): ProjectsRepository

    @Binds
    @OfflineRepository
    internal abstract fun bindsTemplatesRepository(
        templatesRepository: OfflineTemplatesRepository,
    ): TemplatesRepository

    @Binds
    @OfflineRepository
    internal abstract fun bindsTemplatesStatusRepository(
        templatesRepository: OfflineTemplatesStatusRepository,
    ): TemplatesStatusRepository

    @Binds
    @OfflineRepository
    internal abstract fun bindsTasksRepository(
        tasksRepository: OfflineTasksRepository,
    ): TasksRepository

}
