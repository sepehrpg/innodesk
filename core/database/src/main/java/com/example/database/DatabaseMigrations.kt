package com.example.database

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


internal object DatabaseMigrations {

    @RenameColumn(
        tableName = "templates_status",
        fromColumnName = "project_id",
        toColumnName = "template_id",
    )
    class test : AutoMigrationSpec


    val migration6to7 = object : Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 1. ایجاد جدول جدید با تغییرات موردنظر
            database.execSQL(
                """
            CREATE TABLE IF NOT EXISTS tasks_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                user_id INTEGER,
                project_id INTEGER,
                template_status_id INTEGER,
                priority INTEGER,
                percent_complete REAL,
                task_name TEXT NOT NULL,
                descriptions TEXT,
                planned_start_date TEXT,
                planned_end_date TEXT,
                actual_start_date TEXT,
                actual_end_date TEXT,
                planned_budget INTEGER,
                actual_budget INTEGER
            )
            """.trimIndent()
            )

            // 2. کپی داده‌ها از جدول قدیمی به جدول جدید
            database.execSQL(
                """
            INSERT INTO tasks_new (
                id, user_id, project_id, template_status_id, priority, percent_complete,
                task_name, descriptions, planned_start_date, planned_end_date,
                actual_start_date, actual_end_date, planned_budget, actual_budget
            )
            SELECT 
                id, user_id, project_id, template_status_id, priority, percent_complete,
                task_name, descriptions, planned_start_date, planned_end_date,
                actual_start_date, actual_end_date, planned_budget, actual_budget
            FROM tasks
            """.trimIndent()
            )

            // 3. حذف جدول قدیمی
            database.execSQL("DROP TABLE tasks")

            // 4. تغییر نام جدول جدید به نام جدول اصلی
            database.execSQL("ALTER TABLE tasks_new RENAME TO tasks")
        }

    }
}

