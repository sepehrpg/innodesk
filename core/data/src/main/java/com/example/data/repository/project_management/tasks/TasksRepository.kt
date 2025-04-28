package com.example.data.repository.project_management.tasks

import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import com.example.database.model.pm.task.TasksEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task: TasksEntity)

    suspend fun insertTasks(tasks: List<TasksEntity>)

    suspend fun updateTask(task: TasksEntity)

    suspend fun insertOrReplaceTask(task: TasksEntity)

    suspend fun deleteTask(task: TasksEntity)

    fun tasksList(): Flow<List<TasksEntity>>
    fun tasksList(templateStatusId:Int?,projectId:Int?): Flow<List<TasksEntity>>

    fun countTasks(): Flow<Int>

}

