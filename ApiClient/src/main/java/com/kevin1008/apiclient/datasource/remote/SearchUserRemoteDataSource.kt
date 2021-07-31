package com.kevin1008.apiclient.datasource.remote

import com.kevin1008.apiclient.client.ApiClient
import com.kevin1008.apiclient.model.GitHubResponse
import retrofit2.Response

class SearchUserRemoteDataSource(
    private val apiClient: ApiClient<Response<GitHubResponse>>
) : RemoteDataSource<Response<GitHubResponse>> {

    override suspend fun getData(vararg args: Any): Response<GitHubResponse>? {
        return apiClient.requestData(*args)
    }
}