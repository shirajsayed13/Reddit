package com.shiraj.reddit.ui.login.data.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Json(
    @SerializedName("data") @Expose var data: Data? = null
)