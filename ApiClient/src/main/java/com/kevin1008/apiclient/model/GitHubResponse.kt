package com.kevin1008.apiclient.model

import com.google.gson.annotations.SerializedName

/**
 * Search response raw data
 */
data class GitHubResponse(
    @SerializedName("incomplete_results") val isInCompleteResult: Boolean,
    @SerializedName("items") val users: List<GitHubUser>?
)

data class ErrorResponse(
    @SerializedName("message") val message: String?
)

data class GitHubUser(
    @SerializedName("login") val name: String?,
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("type") val userType: String?,
    @SerializedName("id") val id : Int?,
    @SerializedName("node_id") val nodeId : String?,
    @SerializedName("gravatar_id") val gravatarId : String?,
    @SerializedName("url") val url : String?,
    @SerializedName("html_url") val htmlUrl : String?,
    @SerializedName("followers_url") val followersUrl : String?,
    @SerializedName("following_url") val followingUrl : String?,
    @SerializedName("gists_url") val gistsUrl : String?,
    @SerializedName("starred_url") val starredUrl : String?,
    @SerializedName("subscriptions_url") val subscriptionsUrl : String?,
    @SerializedName("organizations_url") val organizationsUrl : String?,
    @SerializedName("repos_url") val reposUrl : String?,
    @SerializedName("events_url") val eventsUrl : String?,
    @SerializedName("received_events_url") val receivedEventsUrl : String?,
    @SerializedName("site_admin") val siteAdmin : Boolean?,
    @SerializedName("score") val score : Int?
)