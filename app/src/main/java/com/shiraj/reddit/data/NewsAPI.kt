package com.shiraj.reddit.data

import kotlinx.coroutines.Deferred

interface NewsAPI {
    fun getNewsApi(after: String, limit: String): Deferred<RedditNewsResponse>
}