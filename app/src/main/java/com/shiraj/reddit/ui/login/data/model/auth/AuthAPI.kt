package com.shiraj.reddit.ui.login.data.model.auth

import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.HashMap

interface AuthAPI {
    @POST("{user}")
    fun signIn(
        @HeaderMap headers: HashMap<String, String>,
        @Path("user") username: String?,
        @Query("user") user: String?,
        @Query("passwd") password: String?,
        @Query("api_type") type: String?
    ): Call<CheckLogin?>?
}