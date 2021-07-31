package com.kevin1008.githubsearchapisample.repository

import com.kevin1008.apiclient.datasource.remote.RemoteDataSource
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.apiclient.model.ErrorResponse
import com.kevin1008.apiclient.model.GitHubResponse
import com.kevin1008.apiclient.model.SearchData
import com.kevin1008.basecore.utils.*
import com.kevin1008.githubsearchapisample.entity.UserEntity
import com.kevin1008.githubsearchapisample.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchUserRepositoryImpl(
    private val searchUserRemoteDataSource: RemoteDataSource<Response<GitHubResponse>>
) : SearchUserRepository {

    override suspend fun getUsers(keyword: SearchData.Keyword): Result<UserEntity> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = searchUserRemoteDataSource.getData(keyword)
            handleResult(response)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getNextPage(nextUrl: SearchData.FetchNextPage): Result<UserEntity> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = searchUserRemoteDataSource.getData(nextUrl)
            handleResult(response, isFetching = true)
        } catch (e: Throwable) {
            Result.Error(e, isFetching = true)
        }
    }

    private fun handleResult(response: Response<GitHubResponse>?, isFetching: Boolean = false): Result<UserEntity> {
        return if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.isInCompleteResult == true) {
                    Result.Error(APIException(ExceptionStatus.INCOMPLETE_RESULT_ERROR), isFetching = isFetching)
                } else {
                    val data = response.body()?.users ?: emptyList()
                    if (data.isEmpty() && !isFetching) {
                        Result.Error(APIException(ExceptionStatus.NO_DATA_ERROR))
                    } else {
                        val nextLink = parseNextLink(response.headers()[LINK])
                        Result.Success(UserEntity(nextUrl = nextLink, users = parseUsers(data)))
                    }
                }
            } else {
                val errorResponse: ErrorResponse? = loadJson(response.errorBody()?.string())
                Result.Error(APIException(ExceptionStatus.CUSTOM_ERROR(errorResponse?.message),
                    response.code()), isFetching = isFetching)
            }
        } else {
            Result.Error(APIException(ExceptionStatus.UNKNOWN_ERROR))
        }
    }

    private fun parseUsers(users: List<GitHubUser>): List<User> {
        return users.asSequence().map {
            User(name = it.name, avatar = it.avatar, userType = it.userType)
        }.toList()
    }

    private fun parseNextLink(link: String?): String {
        link?.split(",")?.asSequence()?.filter {
            it.contains("rel=\"next\"")
        }?.flatMap {
            it.split(";")
        }?.toList()?.takeIf { it.isNotEmpty() }?.forEach {
            val nextLink = it.trim()
            if (nextLink.startsWith("<") && nextLink.endsWith(">")) {
                return nextLink.substring(1, nextLink.length - 1)
            }
        }
        return ""
    }

    companion object {
        private const val LINK = "link"
    }
}