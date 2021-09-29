package com.dip.baloot_danialiranpour.modules

import com.dip.baloot_danialiranpour.api.ArticleApi
import com.dip.baloot_danialiranpour.db.ArticleDao
import com.dip.baloot_danialiranpour.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainRepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        articleApi: ArticleApi,
        articleDao: ArticleDao
    ): MainRepository {

        return MainRepository(articleApi = articleApi, articleDao = articleDao)
    }


}