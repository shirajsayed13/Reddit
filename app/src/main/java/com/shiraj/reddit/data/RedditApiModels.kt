package com.shiraj.reddit.data

class RedditNewsResponse(val data: RedditDataResponse

) {
    override fun toString(): String {
        return "RedditNewsResponse(data=$data)"
    }
}

class RedditDataResponse(
    val children: List<RedditChildrenResponse>,
    val after: String?,
    val before: String?


) {
    override fun toString(): String {
        return "RedditDataResponse(children=$children, after=$after, before=$before)"
    }
}

class RedditChildrenResponse(val data: RedditNewsDataResponse

) {
    override fun toString(): String {
        return "RedditChildrenResponse(data=$data)"
    }
}

class RedditNewsDataResponse(
    val author: String,
    val title: String,
    val num_comments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String?,
    val permalink: String?


) {
    override fun toString(): String {
        return "RedditNewsDataResponse(author='$author', title='$title', num_comments=$num_comments, created=$created, thumbnail='$thumbnail', url=$url, permalink=$permalink)"
    }
}