package com.kevin1008.basecore.interceptor

import com.kevin1008.basecore.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()

        newRequestBuilder.addHeader(ACCEPT, ACCEPT_VALUE)
        if (BuildConfig.GITHUB_TOKEN.isNotEmpty()) {
            newRequestBuilder.addHeader(AUTHORIZATION, BEARER + BuildConfig.GITHUB_TOKEN)
        }

        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val ACCEPT_VALUE = "application/vnd.github.v3+json"
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer "
    }
}