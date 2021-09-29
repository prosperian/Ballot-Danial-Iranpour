package com.dip.baloot_danialiranpour.viewmodels

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.dip.baloot_danialiranpour.api.ArticleListResponse
import com.dip.baloot_danialiranpour.api.Resource
import com.dip.baloot_danialiranpour.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val articles: MutableLiveData<Resource<ArticleListResponse>> = MutableLiveData()
    var articlePage = 1
    var articlesResponse: ArticleListResponse? = null

    init {
        getArticles()
    }

    fun getArticles() = viewModelScope.launch {
        articles.postValue(Resource.Loading())
        val response = mainRepository.getArticles(articlePage)
        articles.postValue(handleBreakingNewsResponse(response))
    }


    private fun handleBreakingNewsResponse(response: ArticleListResponse?): Resource<ArticleListResponse> {

        if (response?.status == "ok") {
            articlePage++
            if (articlesResponse == null) {
                articlesResponse = response
            } else {
                val oldArticles = articlesResponse?.articles
                val newArticles = response?.articles
                oldArticles?.addAll(newArticles)
            }
            return Resource.Success(articlesResponse ?: response)

        }
        return Resource.Error(response?.message)
    }

}