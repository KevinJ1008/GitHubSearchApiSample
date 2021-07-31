package com.kevin1008.githubsearchapisample.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * For UI usage
 */
data class UserEntity(
    val nextUrl: String?,
    val users: List<User>?
)

@Parcelize
data class User(
    val name: String?,
    val avatar: String?,
    val userType: String?
) : Parcelable