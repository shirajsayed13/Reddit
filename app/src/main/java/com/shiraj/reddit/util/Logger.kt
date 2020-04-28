package com.shiraj.reddit.util

import android.util.Log

object Logger {

    private const val TAG = "Reddit"

    fun dt(value: String) {
        Log.d(TAG, "Thread Name: ${Thread.currentThread().name} - $value")
    }
}