package com.example.database.model.pm.templates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates_status")
data class TemplatesStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "project_id") val projectId:Int,

    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "color") val color:String,
    @ColumnInfo(name = "image") val image:String,
)