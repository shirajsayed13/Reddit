package com.shiraj.reddit.util

import com.shiraj.reddit.data.RedditNews

sealed class Resource {
    class Success(val redditNews: RedditNews) : Resource()
    class Error(val message: String?) : Resource()
}