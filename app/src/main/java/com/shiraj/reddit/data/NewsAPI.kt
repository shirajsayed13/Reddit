package com.shiraj.reddit.data

import retrofit2.Call

interface NewsAPI {
    fun getNewsApi(after: String, limit: String): Call<RedditNewsResponse>
}