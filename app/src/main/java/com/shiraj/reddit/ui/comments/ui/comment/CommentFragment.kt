package com.shiraj.reddit.ui.comments.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiraj.reddit.R
import com.shiraj.reddit.RedditApplication
import com.shiraj.reddit.data.comment.RedditComment
import com.shiraj.reddit.util.Resource
import com.shiraj.reddit.util.ViewModelFactory
import com.shiraj.reddit.util.extensions.androidLazy
import com.shiraj.reddit.util.extensions.getViewModel
import kotlinx.android.synthetic.main.comment_fragment.*
import javax.inject.Inject

class CommentFragment : Fragment() {

    companion object {
        fun newInstance(name: String): CommentFragment {
            val args = Bundle()
            args.putString("permalink", name)
            val fragment = CommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: CommentViewModel
    private var redditComment: RedditComment? = null

    private val commentsAdapter by androidLazy { CommentsAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CommentViewModel>
    private val commentViewModel by androidLazy {
        getViewModel<CommentViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RedditApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this).get(CommentViewModel::class.java)
        val permalink = arguments?.getString("permalink")
        val updatedPermalink = permalink?.substring(1)
        if (updatedPermalink != null) {
            viewModel.fetchComments(updatedPermalink)
        }
        viewModel.newsState.observe(viewLifecycleOwner, Observer {
            manageState(it)
        })
        init()
    }

    private fun manageState(resource: Resource?) {
        val state = resource ?: return
        when (state) {
            is Resource.SuccessComment -> {
                redditComment = state.redditComment
                state.redditComment.comments?.let {
                    commentsAdapter.addComments(it)
                    progress_bar.visibility = GONE
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun init() {
        comment_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }

        comment_list.adapter = commentsAdapter
        comment_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }
}
