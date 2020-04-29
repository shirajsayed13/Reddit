package com.shiraj.reddit.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

class ViewModelFactory<T : ViewModel>
@Inject constructor(private val viewModel: @JvmSuppressWildcards Lazy<T>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T
}