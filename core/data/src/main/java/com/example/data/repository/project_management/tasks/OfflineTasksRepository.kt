package com.example.data.repository.project_management.tasks

import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.database.dao.ProjectsManagementDao
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import com.example.database.model.pm.task.TasksEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class OfflineTasksRepository @Inject constructor(
    private val projectManagementDao: ProjectsManagementDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
): TasksRepository {

    override suspend fun insertTask(task: TasksEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertTask(task)
        }
    }

    override suspend fun insertTasks(tasks: List<TasksEntity>) {
        withContext(ioDispatcher){
            projectManagementDao.insertTasks(tasks)
        }
    }

    override suspend fun updateTask(task: TasksEntity) {
        withContext(ioDispatcher){
            projectManagementDao.updateTask(task)
        }
    }

    override suspend fun insertOrReplaceTask(task: TasksEntity) {
        withContext(ioDispatcher){
            projectManagementDao.insertOrReplaceTask(task)
        }
    }

    override suspend fun deleteTask(task: TasksEntity) {
        withContext(ioDispatcher){
            projectManagementDao.deleteTask(task)
        }
    }

    override fun tasksList(): Flow<List<TasksEntity>> =
        projectManagementDao.tasksList().flowOn(ioDispatcher)

    override fun tasksList(templateStatusId: Int?,projectId:Int?): Flow<List<TasksEntity>> =
        projectManagementDao.tasksList(templateStatusId,projectId).flowOn(ioDispatcher)

    override fun countTasks(): Flow<Int> = projectManagementDao.countProjects().flowOn(ioDispatcher)

}