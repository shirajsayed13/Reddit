package com.shiraj.reddit.ui.comments.ui.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.reddit.R
import com.shiraj.reddit.data.RedditNewsItem
import com.shiraj.reddit.data.comment.RedditCommentDataResponse
import com.shiraj.reddit.data.comment.RedditCommentItem
import com.shiraj.reddit.ui.ViewType
import java.util.ArrayList

class CommentsAdapter :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {
    private var comments: ArrayList<RedditCommentItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    init {
        comments = ArrayList()
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindItems(comments[position])
    }

    fun addComments(commentItem: List<RedditCommentItem>) {
        comments = commentItem as ArrayList<RedditCommentItem>
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(commentItem: RedditCommentItem) {
            val comment = itemView.findViewById(R.id.comment) as AppCompatTextView
            val commentAuthor = itemView.findViewById(R.id.commentAuthor) as AppCompatTextView
            comment.text = commentItem.body
            commentAuthor.text = commentItem.author
        }
    }
}