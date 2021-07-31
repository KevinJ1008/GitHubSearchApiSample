package com.kevin1008.githubsearchapisample.repository

import com.kevin1008.basecore.utils.Result
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.apiclient.model.SearchData
import com.kevin1008.githubsearchapisample.entity.UserEntity

interface SearchUserRepository {
    suspend fun getUsers(keyword: SearchData.Keyword): Result<UserEntity>
    suspend fun getNextPage(nextUrl: SearchData.FetchNextPage): Result<UserEntity>
}