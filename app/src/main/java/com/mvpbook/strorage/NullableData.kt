
package com.mvpbook.strorage

data class NewsResponseNullable(
        val articles: List<ArticlesItemNullable?>? = null
)

data class ArticlesItemNullable(
        val author: String? = null,
        val description: String? = null,
        val title: String? = null,
        val img: String? = null,
        val rating: Float? = null,
        var date:String? = null,
        var cost:String? = null
)