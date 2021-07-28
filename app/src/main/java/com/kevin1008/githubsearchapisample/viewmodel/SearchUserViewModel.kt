package com.kevin1008.githubsearchapisample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevin1008.basecore.utils.Result
import com.kevin1008.basecore.utils.toLiveData
import com.kevin1008.basecore.base.BaseViewModel
import com.kevin1008.githubsearchapisample.apiclients.GitHubUser
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCase
import kotlinx.coroutines.launch

class SearchUserViewModel(
    private val searchUserUseCase: SearchUserUseCase
) : BaseViewModel() {

    private val _searchUsers = MutableLiveData<List<GitHubUser>>()
    val searchUsers = _searchUsers.toLiveData()

    fun getUsers(keyword: String) {
        viewModelScope.launch {
            val result = searchUserUseCase.getUsers(keyword = keyword)
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<List<GitHubUser>>) {
        when (result) {
            is Result.Success -> {
                _searchUsers.setValueWithSync(result.data)
            }

            is Result.Error -> {

            }
        }
    }
}