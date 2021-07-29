package com.kevin1008.githubsearchapisample.epoxy

import android.os.Bundle
import com.airbnb.epoxy.EpoxyController
import com.kevin1008.apiclient.model.GitHubUser


class SearchUserEpoxyController : EpoxyController() {

    private var list: List<GitHubUser>? = listOf()

    override fun buildModels() {
        list?.forEachIndexed { index, gitHubUser ->
            searchResultItem {
                id(gitHubUser.hashCode() + index)
                imageUrl(gitHubUser.avatar)
                userType(gitHubUser.userType)
                userName(gitHubUser.name)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_USERS, ArrayList(list))
    }

    override fun onRestoreInstanceState(inState: Bundle?) {
        super.onRestoreInstanceState(inState)
        val restoreList = inState?.getParcelableArrayList<GitHubUser>(KEY_USERS)
        list = restoreList
    }

    fun setUsers(users: List<GitHubUser>) {
        list = users
        requestModelBuild()
    }

    companion object {
        private const val KEY_USERS = "GITHUB_USERS"
    }
}