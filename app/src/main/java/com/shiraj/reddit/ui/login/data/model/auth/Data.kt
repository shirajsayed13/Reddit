package com.shiraj.reddit.ui.login.data.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("modhash") @Expose var modhash: String? = null,

    @SerializedName("cookie")
    @Expose var cookie: String? = null
)