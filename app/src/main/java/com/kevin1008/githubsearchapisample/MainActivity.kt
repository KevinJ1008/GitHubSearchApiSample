package com.kevin1008.githubsearchapisample

import android.os.Bundle
import android.view.LayoutInflater
import com.kevin1008.basecore.base.BaseActivity
import com.kevin1008.githubsearchapisample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (inflater: LayoutInflater) -> ActivityMainBinding
            = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.logo?.setOnClickListener {
            onBackPressed()
        }
    }
}