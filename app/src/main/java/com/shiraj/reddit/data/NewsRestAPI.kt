package com.shiraj.reddit.data

import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditApi) : NewsAPI {

    override fun getNewsApi(after: String, limit: String): Deferred<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}