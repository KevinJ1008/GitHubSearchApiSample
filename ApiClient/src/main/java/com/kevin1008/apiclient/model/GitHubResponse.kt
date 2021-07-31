package com.kevin1008.apiclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GitHubResponse(
    @SerializedName("incomplete_results") val isInCompleteResult: Boolean,
    @SerializedName("items") val users: List<GitHubUser>?
)

data class ErrorResponse(
    @SerializedName("message") val message: String?
)

@Parcelize
data class GitHubUser(
    @SerializedName("login") val name: String?,
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("type") val userType: String?
) : Parcelable