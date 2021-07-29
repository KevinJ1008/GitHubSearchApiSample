package com.kevin1008.githubsearchapisample.di

import com.kevin1008.apiclient.RetrofitManager
import com.kevin1008.apiclient.RetrofitService
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import com.kevin1008.githubsearchapisample.repository.SearchUserRepositoryImpl
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCase
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCaseImpl
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import org.koin.dsl.module

val dataSourceModule = module {
    single<RetrofitService> { RetrofitManager() }
}

val repositoryModule = module {
    factory<SearchUserRepository> { SearchUserRepositoryImpl(get<RetrofitService>().gitHubSearchUserService()) }
}

val useCaseModule = module {
    factory<SearchUserUseCase> { SearchUserUseCaseImpl(get()) }
}

val viewModelModule = module {
    factory { SearchUserViewModel(get()) }
}