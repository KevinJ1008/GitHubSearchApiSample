package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Seems useless of this useCase pattern, but just prepare for future, if we want to change repo but
 * too many viewModel depend on it, use useCase patter could avoid change too many place, and useCase
 * could help us to split some heavy logic or some business logic which cannot classify to ViewModel
 * or Repository
 */
class SearchUserUseCaseImpl(
    private val searchUserRepository: SearchUserRepository
) : SearchUserUseCase {

    override suspend fun getUsers(keyword: String): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        return@withContext searchUserRepository.getUsers(keyword)
    }

    override suspend fun getNextPage(): Result<List<GitHubUser>> = withContext(Dispatchers.IO) {
        return@withContext searchUserRepository.getNextPage()
    }
}