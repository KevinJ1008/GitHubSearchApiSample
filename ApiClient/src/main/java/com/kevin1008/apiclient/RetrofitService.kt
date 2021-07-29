package com.kevin1008.apiclient

import com.kevin1008.apiclient.apiservice.GithubSearchUserService

interface RetrofitService {

    fun gitHubSearchUserService(): GithubSearchUserService
}