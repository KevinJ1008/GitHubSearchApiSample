package com.kevin1008.githubsearchapisample.usecase

import com.kevin1008.basecore.utils.Result
import com.kevin1008.apiclient.model.SearchData
import com.kevin1008.githubsearchapisample.entity.UserEntity
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Seems useless of this useCase pattern, but just prepare for future, if we want to change repo but
 * too many viewModel depend on it, use useCase patter could avoid change too many place, and useCase
 * could help us to split some heavy logic or some business logic which cannot classify to ViewModel
 * or Repository
 */
class SearchUserUseCaseImpl(
    private val searchUserRepository: SearchUserRepository
) : SearchUserUseCase {

    override suspend fun getUsers(keyword: String): Result<UserEntity> = withContext(Dispatchers.IO) {
        val searchKeyword = SearchData.Keyword(keyword = keyword)
        return@withContext searchUserRepository.getUsers(searchKeyword)
    }

    override suspend fun getNextPage(nextUrl: String): Result<UserEntity> = withContext(Dispatchers.IO) {
        val searchNextPage = SearchData.FetchNextPage(nextUrl = nextUrl)
        return@withContext searchUserRepository.getNextPage(searchNextPage)
    }
}