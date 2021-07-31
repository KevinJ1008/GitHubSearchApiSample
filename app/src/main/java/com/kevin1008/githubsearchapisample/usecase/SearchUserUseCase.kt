package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.githubsearchapisample.entity.UserEntity

interface SearchUserUseCase {
    suspend fun getUsers(keyword: String): Result<UserEntity>
    suspend fun getNextPage(nextUrl: String): Result<UserEntity>
}