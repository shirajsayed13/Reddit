package com.shiraj.reddit.data.comment

class RedditCommentResponse(
    val data: RedditDataResponse

) {
    override fun toString(): String {
        return "RedditCommentResponse(data=$data)"
    }
}

class RedditDataResponse(
    val children: List<RedditChildrenCommentResponse>,
    val after: String?,
    val before: String?
) {
    override fun toString(): String {
        return "RedditDataResponse(children=$children, after=$after, before=$before)"
    }
}


class RedditChildrenCommentResponse(
    val data: RedditCommentDataResponse

) {
    override fun toString(): String {
        return "RedditChildrenCommentResponse(data=$data)"
    }
}

class RedditCommentDataResponse(
    val body: String,
    val author: String

) {
    override fun toString(): String {
        return "RedditCommentDataResponse(body='$body', author='$author')"
    }
}