package com.kevin1008.apiclient.client

import android.util.Patterns
import com.kevin1008.apiclient.model.GitHubResponse
import com.kevin1008.apiclient.model.SearchData
import com.kevin1008.apiclient.retrofitapiservice.GithubSearchUserService
import retrofit2.Response

class SearchUserApiClient(
    private val searchUserService: GithubSearchUserService
) : ApiClient<Response<GitHubResponse>> {

    override suspend fun requestData(vararg args: Any): Response<GitHubResponse>? {
        if (args.isEmpty()) {
            return null
        }
        return if (args.getOrNull(0) is SearchData) {
            when (val searchData = args[0] as SearchData) {
                is SearchData.Keyword -> {
                    //search keyword
                    searchUserService.getUsers(searchData.keyword)
                }
                is SearchData.FetchNextPage -> {
                    //fetch
                    searchUserService.getNetPage(searchData.nextUrl)
                }
            }
//            if (Patterns.WEB_URL.matcher(searchData).matches()) {
//                //fetch
//                searchUserService.getNetPage(searchData)
//            } else {
//                //search keyword
//                searchUserService.getUsers(searchData)
//            }
        } else {
            null
        }
    }
}