package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.templates.TemplatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {

    //Basic Command
    //..............................................................................................
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: TemplatesEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<TemplatesEntity>)

    @Update
    suspend fun updateArticle(article: TemplatesEntity)

    @Upsert
    suspend fun insertOrReplaceArticle(article: TemplatesEntity)

    @Query("SELECT * FROM templates")
    fun getAllArticle(): List<TemplatesEntity>

    @Delete
    fun deleteArticle(article: TemplatesEntity)

    @Query("SELECT count(*) FROM templates")
    fun getCountArticles(): Flow<Int>
    //..............................................................................................


    //..............................................................................................
    @Query(
        value = """
        SELECT * FROM templates
        WHERE id IN (:ids)
    """,
    )
    fun getArticlesListByIds(ids: Set<String>): Flow<List<TemplatesEntity>> //or fun getArticlesListByIds(ids: IntArray): Flow<List<ArticlesEntity>> if id be int


    @Query(value = "SELECT * FROM templates ORDER BY id DESC LIMIT :limit")
    fun getArticleWithCustomIdOrderAndLimitCount(limit: Int): Flow<List<TemplatesEntity>>


    @Query(
        value = """
            DELETE FROM templates
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteArticlesByIds(ids: List<String>)


    //@Query("SELECT id FROM templates WHERE type MATCH :query")
    //fun getArticlesIdWithQueryRelatedWithType(query: String): Flow<List<String>>


    @Query(value = "DELETE FROM templates")
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
