package com.example.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.ArticlesEntity
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun insertArticle(article: ArticlesEntity)
    suspend fun insertAllArticles(articles: List<ArticlesEntity>)

    suspend fun updateArticle(article: ArticlesEntity)

    suspend fun insertOrReplaceArticle(article: ArticlesEntity)

    fun getAllArticle(): List<ArticlesEntity>

    fun deleteArticle(article: ArticlesEntity)

    fun getCountArticles(): Flow<Int>
}