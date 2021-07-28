package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.githubsearchapisample.apiclients.GitHubUser
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class SearchUserUseCaseImpl(
    private val searchUserRepository: SearchUserRepository
) : SearchUserUseCase {

    override suspend fun getUsers(keyword: String): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        val encodeKeyword: String
        encodeKeyword = try {
            URLEncoder.encode(keyword, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            keyword
        }
        return@withContext searchUserRepository.getUsers(encodeKeyword)
    }
}