package com.kevin1008.githubsearchapisample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.kevin1008.basecore.base.BaseFragment
import com.kevin1008.githubsearchapisample.R
import com.kevin1008.githubsearchapisample.databinding.FragmentSearchResultBinding
import com.kevin1008.githubsearchapisample.epoxy.SearchUserEpoxyController
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import com.kevin1008.githubsearchapisample.widget.EmptyView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>() {

    private val viewModel: SearchUserViewModel by viewModel()
    private val args: SearchResultFragmentArgs by navArgs()
    private val epoxyController = SearchUserEpoxyController()
    private var layoutEmpty: EmptyView? = null

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentSearchResultBinding = FragmentSearchResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerview?.setController(epoxyController)
        registerObserver()
        if (savedInstanceState == null) {
            viewModel.getUsers(args.keyword)
        } else {
            epoxyController.onRestoreInstanceState(savedInstanceState)
            epoxyController.requestModelBuild()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        super.onDestroyView()
    }

    private fun registerObserver() {
        viewModel.searchUsers.observe(viewLifecycleOwner, {
            //TODO: handle emptyList at first and fetching empty
            epoxyController.setUsers(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                binding?.layoutLoadingContainer?.isVisible = this
            }
        })
        viewModel.loadingError.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                handleError(error = this) { message ->
                    showErrorView(message)
                }
            }
        })
    }

    private fun showErrorView(title: String?) {
        if (layoutEmpty == null) {
            layoutEmpty = binding?.stubEmpty?.inflate() as? EmptyView?
            layoutEmpty?.apply {
                setTitle(title)
                setOnRetryClickListener {
                    layoutEmpty?.isVisible = false
                    viewModel.getUsers(args.keyword)
                }
            }
        }
        layoutEmpty?.isVisible = true
    }
}