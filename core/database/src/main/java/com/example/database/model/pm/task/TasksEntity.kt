package com.example.database.model.pm.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo(name = "project_id") val projectId:Int,
    @ColumnInfo(name = "template_status_id") val templateStatusId:Int,


    @ColumnInfo(name = "priority") val priority:Int,
    @ColumnInfo(name = "percent_complete") val percentComplete:Double,

    @ColumnInfo(name = "task_name") val taskName:String,
    @ColumnInfo(name = "descriptions") val descriptions:String,
    @ColumnInfo(name = "planned_start_date") val plannedStartDate:String,
    @ColumnInfo(name = "planned_end_date") val plannedEndDate:String,
    @ColumnInfo(name = "actual_start_date") val actualStartDate:String,
    @ColumnInfo(name = "actual_end_date") val actualEndDate:String,


    @ColumnInfo(name = "planned_budget") val plannedBudget:Int,
    @ColumnInfo(name = "actual_budget") val actualBudget:Int,

)