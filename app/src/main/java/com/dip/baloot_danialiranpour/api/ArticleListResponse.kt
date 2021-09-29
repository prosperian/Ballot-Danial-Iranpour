package com.dip.baloot_danialiranpour.api

import com.dip.baloot_danialiranpour.models.Article

data class ArticleListResponse(
    val status: String,
    val totalResults: Int?,
    val articles: MutableList<Article>,
    val code: String?,
    val message: String?
) {
}