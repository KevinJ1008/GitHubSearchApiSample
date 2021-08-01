package com.kevin1008.githubsearchapisample.di

import com.kevin1008.apiclient.RetrofitManager
import com.kevin1008.apiclient.RetrofitService
import com.kevin1008.apiclient.client.ApiClient
import com.kevin1008.apiclient.client.SearchUserApiClient
import com.kevin1008.apiclient.datasource.remote.RemoteDataSource
import com.kevin1008.apiclient.datasource.remote.SearchUserRemoteDataSource
import com.kevin1008.apiclient.model.GitHubResponse
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import com.kevin1008.githubsearchapisample.repository.SearchUserRepositoryImpl
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCase
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCaseImpl
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Response

val sourceModule = module {
    single<RetrofitService> { RetrofitManager() }
}

val apiClientModule = module {
    factory<ApiClient<Response<GitHubResponse>>> { SearchUserApiClient(get<RetrofitService>().gitHubSearchUserService()) }
}

val remoteDataSourceModule = module {
    factory<RemoteDataSource<Response<GitHubResponse>>> { SearchUserRemoteDataSource(get()) }
}

val repositoryModule = module {
    factory<SearchUserRepository> { SearchUserRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory<SearchUserUseCase> { SearchUserUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { SearchUserViewModel(get()) }
}