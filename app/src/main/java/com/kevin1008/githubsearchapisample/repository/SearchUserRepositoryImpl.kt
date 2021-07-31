package com.kevin1008.githubsearchapisample.repository

import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.apiclient.apiservice.GithubSearchUserService
import com.kevin1008.apiclient.model.ErrorResponse
import com.kevin1008.apiclient.model.GitHubResponse
import com.kevin1008.basecore.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchUserRepositoryImpl(
    private val searchUserService: GithubSearchUserService
) : SearchUserRepository {

    private var nextUrl: String = ""
    //TODO: could do frontend pagination to optimize memory usage, but need more time to resolve
    // parallel issue, our data is quite small right now, so it won't affect user experience
    private val userList: ArrayList<GitHubUser> = arrayListOf()

    override suspend fun getUsers(keyword: String): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = searchUserService.getUsers(keyword = keyword)
            handleResult(response)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getNextPage(): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = searchUserService.getNetPage(nextUrl)
            handleResult(response, isFetching = true)
        } catch (e: Throwable) {
            Result.Error(e, isFetching = true)
        }
    }

    private fun handleResult(response: Response<GitHubResponse>, isFetching: Boolean = false): Result<List<GitHubUser>> {
        return if (response.isSuccessful) {
                if (response.body()?.isInCompleteResult == true) {
                    Result.Error(APIException(ExceptionStatus.INCOMPLETE_RESULT_ERROR), isFetching = isFetching)
                } else {
                    val data = response.body()?.users ?: emptyList()
                    userList.addAll(data)
                    if (userList.isEmpty()) {
                        Result.Error(APIException(ExceptionStatus.NO_DATA_ERROR))
                    } else {
                        parseNextLink(response.headers()[LINK])
                        Result.Success(userList)
                    }
                }
            } else {
                val errorResponse: ErrorResponse? = loadJson(response.errorBody()?.string())
                Result.Error(APIException(ExceptionStatus.CUSTOM_ERROR(errorResponse?.message),
                    response.code()), isFetching = isFetching)
            }
    }

    private fun parseNextLink(link: String?) {
        link?.split(",")?.asSequence()?.filter {
            it.contains("rel=\"next\"")
        }?.flatMap {
            it.split(";")
        }?.toList()?.takeIf { it.isNotEmpty() }?.forEach {
            val nextLink = it.trim()
            if (nextLink.startsWith("<") && nextLink.endsWith(">")) {
                nextUrl = nextLink.substring(1, nextLink.length - 1)
                return
            }
        } ?: apply {
            nextUrl = ""
        }
    }

    companion object {
        private const val LINK = "link"
    }
}