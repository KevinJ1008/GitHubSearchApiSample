package com.kevin1008.githubsearchapisample.di

import com.kevin1008.githubsearchapisample.apiclients.RetrofitManager
import com.kevin1008.githubsearchapisample.apiclients.RetrofitService
import com.kevin1008.githubsearchapisample.repository.SearchUserRepository
import com.kevin1008.githubsearchapisample.repository.SearchUserRepositoryImpl
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCase
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCaseImpl
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import org.koin.dsl.module

val dataSourceModule = module {
    //TODO: define retrofit service provider
    single<RetrofitService> { RetrofitManager() }
}

val repositoryModule = module {
    //TODO: define repository
    factory<SearchUserRepository> { SearchUserRepositoryImpl(get<RetrofitService>().gitHubSearchUserService()) }
}

val useCaseModule = module {
    factory<SearchUserUseCase> { SearchUserUseCaseImpl(get()) }
}

val viewModelModule = module {
    //TODO: define viewModel
    factory { SearchUserViewModel(get()) }
}