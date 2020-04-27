package com.shiraj.reddit.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String): Deferred<RedditNewsResponse>
}