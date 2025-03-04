package com.example.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.dao.ProjectManagementDao
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates.TemplatesEntity
import com.example.database.model.pm.templates.TemplatesStatusEntity
import com.example.database.utils.ProjectAccessConverter


@Database(
    entities = [
        ProjectsEntity::class,
        TemplatesEntity::class,
        TemplatesStatusEntity::class,
    ],
    version = 5,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
    ],
    exportSchema = true
)
@TypeConverters(ProjectAccessConverter::class)
abstract class RoomDb : RoomDatabase() {
    abstract fun projectManagementDao(): ProjectManagementDao
}


