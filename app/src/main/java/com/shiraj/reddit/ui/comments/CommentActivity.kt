package com.shiraj.reddit.ui.comments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shiraj.reddit.R
import com.shiraj.reddit.ui.comments.ui.comment.CommentFragment

class CommentActivity : AppCompatActivity() {

    companion object {
        var KEY_COMMENT_PERMALINK = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_activity)
        val stringExtra = intent.getStringExtra("permalink")
        KEY_COMMENT_PERMALINK = stringExtra
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CommentFragment.newInstance())
                .commitNow()
        }

    }
}
