package com.kevin1008.apiclient.retrofitapiservice

import com.kevin1008.apiclient.model.GitHubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubSearchUserService {

    @GET("search/users")
    suspend fun getUsers(@Query("q") keyword: String): Response<GitHubResponse>

    @GET
    suspend fun getNetPage(@Url url: String): Response<GitHubResponse>
}