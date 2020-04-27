package com.shiraj.reddit.ui.login.data

import android.util.Log
import com.shiraj.reddit.di.module.RetrofitService
import com.shiraj.reddit.ui.login.data.model.LoggedInUser
import com.shiraj.reddit.ui.login.data.model.auth.AuthAPI
import com.shiraj.reddit.ui.login.data.model.auth.CheckLogin
import com.shiraj.reddit.ui.login.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class LoginDataSource() {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            loginAPICall(username, password)
            return Result.Success(LoggedInUser("Shiraj","ShirajSayed"))

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
    }

    fun loginAPICall(username: String, password: String) {
        val authAPI: AuthAPI = RetrofitService.provideRetrofit().create(AuthAPI::class.java)

        val headerMap =
            HashMap<String, String>()
        headerMap["Content-Type"] = "application/json"


        val call: Call<CheckLogin?>? =
            authAPI.signIn(headerMap, username, username, password, "json")

        call?.enqueue(object : Callback<CheckLogin> {
            override fun onResponse(
                call: Call<CheckLogin>,
                response: Response<CheckLogin>
            ) {
                try {
                    Log.d(
                        LoginActivity.TAG,
                        "onResponse: Server Response: $response"
                    )
                    val modhash = response.body()!!.json?.data?.modhash
                    val cookie = response.body()!!.json?.data?.cookie
                    if (modhash.isNullOrEmpty() || cookie.isNullOrEmpty()) {
                        print("LOGIN UNSUCCESSFUL")
                    }
                    if (modhash != "") {
                        print("LOGIN SUCCESSFUL")
                    }
                } catch (e: NullPointerException) {
                    Log.e(
                        LoginActivity.TAG,
                        "onResponse: NullPointerException: " + e.message
                    )
                }
            }

            override fun onFailure(call: Call<CheckLogin>, t: Throwable) {
                Log.e(LoginActivity.TAG, "onFailure: Unable to retrieve RSS: " + t.message)
            }
        })
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<CheckLogin>) {

}

