package com.example.database.model.pm.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "project_access_id") val projectAccessId:Int,
    @ColumnInfo(name = "template_id") val templateId:Int? = null,
    @ColumnInfo(name = "user_id") val userId:Int? = null,


    @ColumnInfo(name = "description") val description:String,

    @ColumnInfo(name = "actual_start_date") val actualStartDate:String,
    @ColumnInfo(name = "actual_end_date") val actualEndDate:String,
    @ColumnInfo(name = "planned_start_date") val plannedStartDate:String,
    @ColumnInfo(name = "planned_end_date") val plannedEndDate:String,

    @ColumnInfo(name = "image") val image:String,
    @ColumnInfo(name = "color") val color:String,
)