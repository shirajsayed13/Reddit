package com.shiraj.reddit.data

import com.shiraj.reddit.data.news.RedditNewsResponse
import retrofit2.Call

interface NewsAPI {
    fun getNewsApi(after: String, limit: String): Call<RedditNewsResponse>
}