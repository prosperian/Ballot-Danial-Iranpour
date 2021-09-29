package com.dip.baloot_danialiranpour.db

import androidx.room.*
import com.dip.baloot_danialiranpour.models.Article

@Dao
interface ArticleDao {

    @Transaction
    @Query("select * from Article")
    suspend fun getArticles(): MutableList<Article>

    @Transaction
    @Query("select * from Article where id = :id")
    fun getArticle(id: Int): Article

    @Insert(entity = Article::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

}