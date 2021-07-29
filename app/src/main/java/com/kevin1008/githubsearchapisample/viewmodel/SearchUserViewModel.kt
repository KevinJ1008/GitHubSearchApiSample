package com.kevin1008.githubsearchapisample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevin1008.basecore.utils.Result
import com.kevin1008.basecore.utils.toLiveData
import com.kevin1008.basecore.base.BaseViewModel
import com.kevin1008.apiclient.model.GitHubUser
import com.kevin1008.basecore.utils.Event
import com.kevin1008.basecore.utils.InCompleteResultError
import com.kevin1008.githubsearchapisample.usecase.SearchUserUseCase
import kotlinx.coroutines.launch

class SearchUserViewModel(
    private val searchUserUseCase: SearchUserUseCase
) : BaseViewModel() {

    private val _searchUsers = MutableLiveData<List<GitHubUser>>()
    val searchUsers = _searchUsers.toLiveData()

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading = _isLoading.toLiveData()

    private val _loadingError = MutableLiveData<Event<Result.Error>>()
    val loadingError = _loadingError.toLiveData()

    fun getUsers(keyword: String) {
        _isLoading.setValueWithSync(Event(content = true))
        viewModelScope.launch {
            val result = searchUserUseCase.getUsers(keyword = keyword)
            handleResult(result = result)
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            val result = searchUserUseCase.getNextPage()
            handleResult(result = result)
        }
    }

    private fun handleResult(result: Result<List<GitHubUser>>) {
        _isLoading.setValueWithSync(Event(content = false))
        when (result) {
            is Result.Success -> {
                _searchUsers.setValueWithSync(result.data)
            }

            is Result.Error -> {
                _loadingError.setValueWithSync(Event(result))
            }
        }
    }
}