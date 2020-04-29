package com.shiraj.reddit.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.reddit.R
import com.shiraj.reddit.util.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.item_loading)
    )
}