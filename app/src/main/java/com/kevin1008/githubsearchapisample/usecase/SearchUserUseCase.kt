package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.githubsearchapisample.apiclients.GitHubUser

interface SearchUserUseCase {

    suspend fun getUsers(keyword: String): Result<List<GitHubUser>>
}