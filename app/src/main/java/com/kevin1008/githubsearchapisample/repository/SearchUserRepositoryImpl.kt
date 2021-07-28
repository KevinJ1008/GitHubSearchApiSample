package com.kevin1008.githubsearchapisample.repository

import com.kevin1008.basecore.utils.Result
import com.kevin1008.githubsearchapisample.apiclients.GitHubResponse
import com.kevin1008.githubsearchapisample.apiclients.GitHubUser
import com.kevin1008.githubsearchapisample.apiclients.GithubSearchUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchUserRepositoryImpl(
    private val searchUserService: GithubSearchUserService
) : SearchUserRepository {

    private var nextUrl: String? = null

    override suspend fun getUsers(keyword: String): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = searchUserService.getUsers(keyword = keyword)
            //TODO: parse "link" from header for nextUrl
            val body = response.body()
            if (response.isSuccessful && body?.inCompleteResult == true) {
                val data = body.users ?: emptyList()
                Result.Success(data)
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getNextPage(url: String): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
//        searchUserService.getNetPage(url)
        Result.Success(emptyList())
    }

//    private fun handleResult(body: ): Result<List<GitHubUser>> {
//        return try {
//            val response = searchUserService.getUsers(keyword = keyword)
//            val body = response.body()
//            if (response.isSuccessful && body?.inCompleteResult == true) {
//                val data = body.users ?: emptyList()
//                Result.Success(data)
//            } else {
//                Result.Error(HttpException(response))
//            }
//        } catch (e: Throwable) {
//            Result.Error(e)
//        }
//    }
}