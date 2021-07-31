package com.kevin1008.githubsearchapisample.epoxy

import android.os.Bundle
import com.airbnb.epoxy.EpoxyController
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.githubsearchapisample.entity.User


class SearchUserEpoxyController : EpoxyController() {

    var isLoading: Boolean = false
    //TODO: could do frontend pagination to optimize memory usage, but need more time to resolve
    // parallel issue, our data is quite small right now, so it won't affect user experience
    private var list: ArrayList<User> = arrayListOf()

    override fun buildModels() {
        list.forEachIndexed { index, gitHubUser ->
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
        outState.putParcelableArrayList(KEY_USERS, list)
    }

    override fun onRestoreInstanceState(inState: Bundle?) {
        super.onRestoreInstanceState(inState)
        inState?.getParcelableArrayList<User>(KEY_USERS)?.apply {
            list = this
        }
    }

    fun setUsers(users: List<User>?) {
        isLoading = false
        users?.apply {
            list.addAll(this)
            requestModelBuild()
        }
    }

    companion object {
        private const val KEY_USERS = "GITHUB_USERS"
    }
}