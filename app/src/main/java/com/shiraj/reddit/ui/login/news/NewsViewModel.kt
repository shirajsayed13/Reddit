package com.shiraj.reddit.ui.login.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiraj.reddit.data.RedditApi
import com.shiraj.reddit.data.news.RedditNews
import com.shiraj.reddit.data.news.RedditNewsItem
import com.shiraj.reddit.data.news.RedditNewsResponse
import com.shiraj.reddit.di.module.RetrofitService
import com.shiraj.reddit.ui.login.ui.login.LoginActivity
import com.shiraj.reddit.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor() : ViewModel() {

    val newsState: MutableLiveData<Resource> = MutableLiveData()

    fun fetchNews(after: String, limit: String = "10") {
        val newsAPI: RedditApi = RetrofitService.provideRetrofit().create(RedditApi::class.java)

        newsAPI.getTop(after, limit)
            .enqueue(object : Callback<RedditNewsResponse> {
                override fun onResponse(
                    call: Call<RedditNewsResponse>,
                    response: Response<RedditNewsResponse>
                ) {
                    val dataResponse = response.body()
                    val news = process(dataResponse)
                    newsState.postValue(Resource.Success(news))
                }

                override fun onFailure(call: Call<RedditNewsResponse>, t: Throwable) {
                    Log.e(LoginActivity.TAG, "onFailure: Unable to retrieve Feeds: " + t.message)
                }
            })

    }

    private fun process(response: RedditNewsResponse?): RedditNews {
        val dataResponse = response?.data
        val news = dataResponse?.children?.map {
            val item = it.data
            RedditNewsItem(
                item.author, item.title, item.num_comments,
                item.created, item.thumbnail, item.url, item.permalink, item.body
            )
        }
        return RedditNews(
            dataResponse?.after.orEmpty(),
            dataResponse?.before.orEmpty(),
            news
        )
    }
}