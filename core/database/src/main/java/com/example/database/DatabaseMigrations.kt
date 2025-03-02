

package com.example.database

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec


internal object DatabaseMigrations {

    @RenameColumn(
        tableName = "templates_status",
        fromColumnName = "project_id",
        toColumnName = "template_id",
    )
    class Schema7to8 : AutoMigrationSpec

}
