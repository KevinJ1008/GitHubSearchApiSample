package com.kevin1008.apiclient

import com.kevin1008.apiclient.retrofitapiservice.GithubSearchUserService

interface RetrofitService {

    fun gitHubSearchUserService(): GithubSearchUserService
}