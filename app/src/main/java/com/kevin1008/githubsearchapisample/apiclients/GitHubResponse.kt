package com.kevin1008.githubsearchapisample.apiclients

import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("incomplete_results") val inCompleteResult: Boolean,
    @SerializedName("items") val users: List<GitHubUser>?
)

data class GitHubUser(
    @SerializedName("login") val name: String?,
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("type") val userType: String?
)