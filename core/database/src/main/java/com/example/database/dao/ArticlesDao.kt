package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.ArticlesEntity
import com.example.database.model.old.RecentSearchQueryEntity
import com.example.database.model.old.TopicEntity
import com.example.database.model.old.TopicFtsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    //Basic Command
    //..............................................................................................
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticlesEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticlesEntity>)

    @Update
    suspend fun updateArticle(article: ArticlesEntity)

    @Upsert
    suspend fun insertOrReplaceArticle(article: ArticlesEntity)

    @Query("SELECT * FROM articles")
    fun getAllArticle(): List<ArticlesEntity>

    @Delete
    fun deleteArticle(article: ArticlesEntity)

    @Query("SELECT count(*) FROM articles")
    fun getCountArticles(): Flow<Int>
    //..............................................................................................


    //..............................................................................................
    @Query(
        value = """
        SELECT * FROM articles
        WHERE id IN (:ids)
    """,
    )
    fun getArticlesListByIds(ids: Set<String>): Flow<List<ArticlesEntity>> //or fun getArticlesListByIds(ids: IntArray): Flow<List<ArticlesEntity>> if id be int


    @Query(value = "SELECT * FROM articles ORDER BY id DESC LIMIT :limit")
    fun getArticleWithCustomIdOrderAndLimitCount(limit: Int): Flow<List<ArticlesEntity>>


    @Query(
        value = """
            DELETE FROM articles
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteArticlesByIds(ids: List<String>)


    @Query("SELECT id FROM articles WHERE type MATCH :query")
    fun getArticlesIdWithQueryRelatedWithType(query: String): Flow<List<String>>


    @Query(value = "DELETE FROM articles")
    suspend fun clearArticleEntity()
    //..............................................................................................
}

/**
 * Sample Of Complex Query::
 * */
/*
@Transaction
@Query(
    value = """
            SELECT id FROM news_resources
            WHERE
                CASE WHEN :useFilterNewsIds
                    THEN id IN (:filterNewsIds)
                    ELSE 1
                END
             AND
                CASE WHEN :useFilterTopicIds
                    THEN id IN
                        (
                            SELECT news_resource_id FROM news_resources_topics
                            WHERE topic_id IN (:filterTopicIds)
                        )
                    ELSE 1
                END
            ORDER BY publish_date DESC
    """,
)
fun getNewsResourceIds(
    useFilterTopicIds: Boolean = false,
    filterTopicIds: Set<String> = emptySet(),
    useFilterNewsIds: Boolean = false,
    filterNewsIds: Set<String> = emptySet(),

   //

   //

): Flow<List<String>>*/
