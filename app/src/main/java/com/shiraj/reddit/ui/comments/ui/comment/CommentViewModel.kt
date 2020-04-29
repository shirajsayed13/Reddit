package com.shiraj.reddit.ui.comments.ui.comment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiraj.reddit.data.RedditApi
import com.shiraj.reddit.data.comment.*
import com.shiraj.reddit.di.module.RetrofitService
import com.shiraj.reddit.ui.login.ui.login.LoginActivity
import com.shiraj.reddit.util.Logger
import com.shiraj.reddit.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CommentViewModel @Inject constructor(): ViewModel() {

    val newsState: MutableLiveData<Resource> = MutableLiveData()

    fun fetchComments(permalink: String) {
        val commentAPI: RedditApi = RetrofitService.provideRetrofit().create(RedditApi::class.java)

        commentAPI.getComments(permalink)
            .enqueue(object : Callback<List<RedditCommentResponse>> {
                override fun onResponse(
                    call: Call<List<RedditCommentResponse>>,
                    response: Response<List<RedditCommentResponse>>
                ) {
                    val dataResponse = response.body()
                    Logger.dt("CHECK THIS VALUE dataResponse commentAPI ${dataResponse.toString()}")
                    val comments = process(dataResponse)
                    newsState.postValue(Resource.SuccessComment(comments))
                }

                override fun onFailure(call: Call<List<RedditCommentResponse>>, t: Throwable) {
                    Log.e(LoginActivity.TAG, "onFailure: Unable to retrieve Feeds: " + t.message)
                }
            })
    }

    private fun process(response: List<RedditCommentResponse>?): RedditComment {
        val listCommentItem = ArrayList<RedditCommentItem>()
        val dataResponse = response?.get(1)?.data
        for (i in 1..3 ) {
            dataResponse?.children?.map {
                val item = it.data
                listCommentItem.add(RedditCommentItem(item.author, item.body))
            }
        }
        return RedditComment(
            dataResponse?.after.orEmpty(),
            dataResponse?.before.orEmpty(),
            listCommentItem)
    }
}
