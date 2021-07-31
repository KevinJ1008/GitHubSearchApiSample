package com.kevin1008.apiclient.datasource.remote

interface RemoteDataSource<T> {
    suspend fun getData(vararg args: Any): T?
}