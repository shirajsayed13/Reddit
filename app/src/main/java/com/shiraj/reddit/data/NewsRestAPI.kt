package com.shiraj.reddit.data

import com.shiraj.reddit.data.news.RedditNewsResponse
import retrofit2.Call
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditApi) : NewsAPI {

    override fun getNewsApi(after: String, limit: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}