package com.shiraj.reddit.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiraj.reddit.data.*
import com.shiraj.reddit.di.module.NetworkModule
import com.shiraj.reddit.di.module.RetrofitService
import com.shiraj.reddit.ui.login.ui.login.LoginActivity
import com.shiraj.reddit.util.Logger
import com.shiraj.reddit.util.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
            RedditNewsItem(item.author, item.title, item.num_comments,
                item.created, item.thumbnail, item.url, item.permalink, item.body)
        }
        return RedditNews(
            dataResponse?.after.orEmpty(),
            dataResponse?.before.orEmpty(),
            news)
    }
}