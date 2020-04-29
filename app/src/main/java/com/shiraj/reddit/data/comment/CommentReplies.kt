package com.shiraj.reddit.data.comment

import java.io.Serializable


class RedditCommentRepliesResponse(
    val data: RedditCommentRepliesDataResponse

) : Serializable

class RedditCommentRepliesDataResponse(
    val children: List<RedditChildrenRepliesCommentResponse>
) {
    override fun toString(): String {
        return "RedditDataResponse(children=$children)"
    }
}

class RedditChildrenRepliesCommentResponse(
    val data: RedditCommentRepliesData

) {
    override fun toString(): String {
        return "RedditChildrenCommentResponse(data=$data)"
    }
}

class RedditCommentRepliesData(
    val body: String?,
    val author: String
)