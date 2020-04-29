package com.shiraj.reddit.ui.login.data.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckLogin(
    @SerializedName("json")
    @Expose val json: Json? = null
)