package com.shiraj.reddit.util

import com.shiraj.reddit.data.comment.RedditComment
import com.shiraj.reddit.data.news.RedditNews

sealed class Resource {
    class Success(val redditNews: RedditNews) : Resource()
    class SuccessComment(val redditComment: RedditComment) : Resource()
    class Error(val message: String?) : Resource()
}