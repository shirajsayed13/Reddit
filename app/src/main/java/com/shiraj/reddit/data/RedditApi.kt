package com.shiraj.reddit.data

import com.shiraj.reddit.data.comment.RedditCommentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String): Call<RedditNewsResponse>

    @GET("/{permalink}/.json")
    fun getComments(@Path("permalink") permalink: String): Call<List<RedditCommentResponse>>
}