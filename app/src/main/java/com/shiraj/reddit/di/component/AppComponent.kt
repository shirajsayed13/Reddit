package com.shiraj.reddit.di.component

import com.shiraj.reddit.di.module.AppModule
import com.shiraj.reddit.di.module.NetworkModule
import com.shiraj.reddit.di.module.NewsModule
import com.shiraj.reddit.ui.NewsFragment
import com.shiraj.reddit.ui.comments.ui.comment.CommentFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, NewsModule::class])
interface AppComponent {

    fun inject(newsFragment: NewsFragment)

    fun inject(commentFragment: CommentFragment)
}