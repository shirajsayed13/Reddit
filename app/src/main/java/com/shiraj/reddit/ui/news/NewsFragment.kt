package com.shiraj.reddit.ui.news

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiraj.reddit.R
import com.shiraj.reddit.RedditApplication
import com.shiraj.reddit.data.news.RedditNews
import com.shiraj.reddit.ui.comments.CommentActivity
import com.shiraj.reddit.util.InfiniteScrollListener
import com.shiraj.reddit.util.Resource
import com.shiraj.reddit.util.ViewModelFactory
import com.shiraj.reddit.util.extensions.androidLazy
import com.shiraj.reddit.util.extensions.getViewModel
import com.shiraj.reddit.util.extensions.inflate
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment : Fragment(), NewsDelegateAdapter.onViewSelectedListener {

    override fun onItemSelected(url: String?) {
        if (url.isNullOrEmpty()) {
            Toast.makeText(context, "isNullOrEmpty", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra("permalink", url)
            startActivity(intent)
        }
    }

    companion object {
        private const val KEY_REDDIT_NEWS = "RedditNews"
    }

    private var redditNews: RedditNews? = null
    private val newsAdapter by androidLazy {
        NewsAdapter(
            this
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<NewsViewModel>
    private val newsViewModel by androidLazy {
        getViewModel<NewsViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RedditApplication.appComponent.inject(this)
        requestNews()
        newsViewModel.newsState.observe(this, Observer {
            manageState(it)
        })
    }

    private fun manageState(resource: Resource?) {
        val state = resource ?: return
        when (state) {
            is Resource.Success -> {
                redditNews = state.redditNews
                state.redditNews.news?.let { newsAdapter.addNews(it) }
            }
            is Resource.Error -> {
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }

        news_list.adapter = newsAdapter

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            redditNews?.news?.let { newsAdapter.clearAndAddNews(it) }
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = newsAdapter.getNews()
        if (redditNews != null && news.isNotEmpty()) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        if (isNetworkConnected()) {
            newsViewModel.fetchNews(redditNews?.after.orEmpty())
        } else {
            Toast.makeText(context, "No NetWork Connectivity", Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}