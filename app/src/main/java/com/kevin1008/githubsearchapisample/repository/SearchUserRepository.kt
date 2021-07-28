package com.kevin1008.githubsearchapisample.repository

import com.kevin1008.basecore.utils.Result
import com.kevin1008.githubsearchapisample.apiclients.GitHubUser

interface SearchUserRepository {
    suspend fun getUsers(keyword: String): Result<List<GitHubUser>>
    suspend fun getNextPage(url: String): Result<List<GitHubUser>>
}