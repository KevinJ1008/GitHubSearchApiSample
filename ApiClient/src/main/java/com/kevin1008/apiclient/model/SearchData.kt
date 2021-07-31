package com.kevin1008.apiclient.model

/**
 * Make call concisely which could let condition clearly
 */
sealed class SearchData {
    data class Keyword(val keyword: String) : SearchData()
    data class FetchNextPage(val nextUrl: String) : SearchData()
}
