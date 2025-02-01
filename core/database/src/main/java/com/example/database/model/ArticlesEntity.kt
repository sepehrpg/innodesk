package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant


@Entity(
    tableName = "articles",
)
data class ArticlesEntity(
    @PrimaryKey //or use @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    //@ColumnInfo(name = "publish_date")
    //val publishDate: Instant,
    val type: String,
    val date: String,
)