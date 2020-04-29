package com.shiraj.reddit.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.reddit.R
import com.shiraj.reddit.data.news.RedditNewsItem
import com.shiraj.reddit.util.extensions.inflate
import com.shiraj.reddit.util.extensions.loadImg
import kotlinx.android.synthetic.main.item_news.view.*

class NewsDelegateAdapter(val viewActions: onViewSelectedListener) : ViewTypeDelegateAdapter {

    interface onViewSelectedListener {
        fun onItemSelected(url: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.item_news)
    ) {

        private val imgThumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments

        fun bind(item: RedditNewsItem) {
            item.thumbnail?.let { imgThumbnail.loadImg(it) }
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"

            super.itemView.setOnClickListener { viewActions.onItemSelected(item.permalink) }
        }
    }
}