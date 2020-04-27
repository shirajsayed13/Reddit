package com.shiraj.reddit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiraj.reddit.data.NewsAPI
import com.shiraj.reddit.data.RedditNews
import com.shiraj.reddit.data.RedditNewsItem
import com.shiraj.reddit.data.RedditNewsResponse
import com.shiraj.reddit.util.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsViewModel @Inject constructor(
    private val api: NewsAPI,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    val newsState: MutableLiveData<Resource> = MutableLiveData()

    fun fetchNews(after: String, limit: String = "10") = GlobalScope.launch(coroutineContext) {
        try {
            val result = api.getNewsApi(after, limit).await()
            val news = process(result)
            newsState.postValue(Resource.Success(news))
        } catch (e: Throwable) {
            println(e.stackTrace)
            newsState.postValue(Resource.Error(e.message))
        }
    }

    private fun process(response: RedditNewsResponse): RedditNews {
        val dataResponse = response.data
        val news = dataResponse.children.map {
            val item = it.data
            RedditNewsItem(
                item.author, item.title, item.num_comments,
                item.created, item.thumbnail, item.url, item.permalink
            )
        }
        return RedditNews(
            dataResponse.after.orEmpty(),
            dataResponse.before.orEmpty(),
            news
        )
    }
}