package com.shiraj.reddit.ui.login.news

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.reddit.ui.login.news.ViewType

interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}