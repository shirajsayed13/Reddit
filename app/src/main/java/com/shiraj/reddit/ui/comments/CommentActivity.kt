package com.shiraj.reddit.ui.comments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shiraj.reddit.R
import com.shiraj.reddit.ui.comments.ui.comment.CommentFragment

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_activity)
        val permalink = intent.getStringExtra("permalink")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_comment, CommentFragment.newInstance(permalink))
                .commitNow()
        }
    }
}
