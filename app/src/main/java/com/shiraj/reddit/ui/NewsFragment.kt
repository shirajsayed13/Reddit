package com.shiraj.reddit.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.AlteredCharSequence.make
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiraj.reddit.R
import com.shiraj.reddit.RedditApplication
import com.shiraj.reddit.data.RedditNews
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
            Toast.makeText(context,"isNullOrEmpty", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    companion object {
        private const val KEY_REDDIT_NEWS = "RedditNews"
    }

    private var redditNews: RedditNews? = null
    private val newsAdapter by androidLazy { NewsAdapter(this) }

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
                newsAdapter.addNews(state.redditNews.news)
            }
            is Resource.Error -> {
                Toast.makeText(context,"Error Occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
    }

    private fun requestNews() {
        newsViewModel.fetchNews(redditNews?.after.orEmpty())
    }
}