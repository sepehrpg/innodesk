package com.example.data.repository

import com.example.database.dao.ArticlesDao
import com.example.database.model.ArticlesEntity
import com.example.network.AppDispatchers
import com.example.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineArticleRepository @Inject constructor(
    private val articleDao:ArticlesDao,
    //@Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ArticleRepository {

    override suspend fun insertArticle(article: ArticlesEntity) {
        articleDao.insertArticle(article)
    }

    override suspend fun insertAllArticles(articles: List<ArticlesEntity>) {
        articleDao.insertAllArticles(articles)
    }

    override suspend fun updateArticle(article: ArticlesEntity) {
        articleDao.updateArticle(article)
    }

    override suspend fun insertOrReplaceArticle(article: ArticlesEntity) {
        articleDao.insertOrReplaceArticle(article)
    }

    override fun getAllArticle(): List<ArticlesEntity> = articleDao.getAllArticle()

    override fun deleteArticle(article: ArticlesEntity) {
        articleDao.deleteArticle(article)
    }

    override fun getCountArticles(): Flow<Int> = articleDao.getCountArticles()

}