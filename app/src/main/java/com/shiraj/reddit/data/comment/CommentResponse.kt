package com.shiraj.reddit.data.comment

data class RedditCommentResponse(
    val data: RedditDataResponse

)

data class RedditDataResponse(
    val children: List<RedditChildrenCommentResponse>,
    val after: String?,
    val before: String?
)


data class RedditChildrenCommentResponse(
    val data: RedditCommentDataResponse

)

data class RedditCommentDataResponse(
    val body: String,
    val author: String
)

data class RedditCommentReplies(
    val data: RedditCommentResponse

)