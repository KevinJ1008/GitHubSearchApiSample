package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.apiclient.model.GitHubUser

interface SearchUserUseCase {

    suspend fun getUsers(keyword: String): Result<List<GitHubUser>>
    suspend fun getNextPage(): Result<List<GitHubUser>>
}