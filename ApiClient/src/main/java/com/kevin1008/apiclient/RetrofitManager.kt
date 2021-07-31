package com.kevin1008.apiclient

import com.kevin1008.apiclient.retrofitapiservice.GithubSearchUserService
import com.kevin1008.basecore.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager : RetrofitService {

    private val searchUserService: GithubSearchUserService

    override fun gitHubSearchUserService(): GithubSearchUserService = searchUserService

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val TIMEOUT = 10L
    }

    init {
        val authInterceptor = AuthInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        searchUserService = retrofit.create(GithubSearchUserService::class.java)
    }
}