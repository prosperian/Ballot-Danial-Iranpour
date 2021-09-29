package com.dip.baloot_danialiranpour.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.dip.baloot_danialiranpour.api.ArticleApi
import com.dip.baloot_danialiranpour.api.ArticleListResponse
import com.dip.baloot_danialiranpour.db.ArticleDao
import com.dip.baloot_danialiranpour.models.Article
import com.dip.baloot_danialiranpour.utils.Utils
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class MainRepository @Inject constructor(
    private val articleApi: ArticleApi,
    private val articleDao: ArticleDao
) {

    @WorkerThread
    suspend fun getArticles(articlePage: Int): ArticleListResponse? {
        val articles: MutableList<Article> = articleDao.getArticles()
        return articleApi.getArticles(
            Utils.TOKEN,
            country = "us",
            page = articlePage,
            pageSize = Utils.PAGE_SIZE
        ).body()
//        return if (articles.isEmpty()) {
//            articleApi.getArticles(Utils.TOKEN, country = "us").body()
//        } else {
//            ArticleListResponse(
//                status = "ok",
//                totalResults = articles.size,
//                articles = articles,
//                null,
//                null
//            )
//        }
    }

}