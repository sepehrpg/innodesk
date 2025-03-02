package com.example.database.model.pm.templates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates_status")
data class TemplatesStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name = "order") val order:Int,

    @ColumnInfo(name = "template_id") val templateId:Int = 0,

    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "color") val color:String? = null ,
    @ColumnInfo(name = "image") val image:String? = null ,
)