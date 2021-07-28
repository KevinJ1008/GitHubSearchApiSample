package com.kevin1008.githubsearchapisample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.kevin1008.basecore.base.BaseActivity
import com.kevin1008.githubsearchapisample.databinding.ActivityMainBinding
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val searchUserViewModel: SearchUserViewModel by viewModel()

    override val bindingInflater: (inflater: LayoutInflater) -> ActivityMainBinding
            = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.logo?.setOnClickListener {
            //FIXME: test inject flow, need to delete
            searchUserViewModel.getUsers("kevin")
        }
        registerObserver()
    }

    private fun registerObserver() {
        //FIXME: test inject flow, need to delete
        searchUserViewModel.searchUsers.observe(this, {
            Log.d("MainActivity", it.toString())
        })
    }
}