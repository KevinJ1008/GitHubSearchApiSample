package com.kevin1008.apiclient.client

interface ApiClient<T> {
    suspend fun requestData(vararg args: Any): T?
}