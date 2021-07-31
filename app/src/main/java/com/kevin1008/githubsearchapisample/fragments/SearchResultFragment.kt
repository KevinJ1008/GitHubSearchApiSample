package com.kevin1008.githubsearchapisample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.kevin1008.basecore.base.BaseFragment
import com.kevin1008.basecore.utils.ExceptionStatus
import com.kevin1008.githubsearchapisample.databinding.FragmentSearchResultBinding
import com.kevin1008.githubsearchapisample.epoxy.SearchUserEpoxyController
import com.kevin1008.githubsearchapisample.viewmodel.SearchUserViewModel
import com.kevin1008.widget.customview.EmptyView
import com.kevin1008.widget.listener.LoadMoreListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>() {

    private val viewModel: SearchUserViewModel by viewModel()
    private val args: SearchResultFragmentArgs by navArgs()
    private val epoxyController = SearchUserEpoxyController()
    private var layoutEmpty: EmptyView? = null
    private var nextUrl: String = ""

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentSearchResultBinding = FragmentSearchResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewSetting()
        registerObserver()
        if (savedInstanceState == null) {
            viewModel.getUsers(args.keyword)
        } else {
            nextUrl = savedInstanceState.getString(NEXT_URL, "")
            epoxyController.onRestoreInstanceState(savedInstanceState)
            epoxyController.requestModelBuild()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NEXT_URL, nextUrl)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        super.onDestroyView()
    }

    override fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus?) {
        super.showCustomErrorView(message, exceptionStatus)
        if (layoutEmpty == null) {
            layoutEmpty = binding?.stubEmpty?.inflate() as? EmptyView?
            layoutEmpty?.apply {
                setTitle(message)
                if (exceptionStatus is ExceptionStatus.NO_DATA_ERROR) {
                    setButtonVisible(isVisible = false)
                } else {
                    setOnRetryClickListener {
                        layoutEmpty?.isVisible = false
                        viewModel.getUsers(args.keyword)
                    }
                }
            }
        }
        layoutEmpty?.isVisible = true
    }

    private fun registerObserver() {
        viewModel.searchUsers.observe(viewLifecycleOwner, {
            it?.apply {
                this@SearchResultFragment.nextUrl = this.nextUrl ?: ""
                epoxyController.setUsers(this.users)
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                binding?.layoutLoadingContainer?.isVisible = this
            }
        })
        viewModel.loadingError.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                epoxyController.isLoading = false
                handleError(error = this, callback = this@SearchResultFragment)
            }
        })
    }

    private fun initViewSetting() {
        binding?.recyclerview?.apply {
            this.setController(epoxyController)
            val loadMoreListener = object : LoadMoreListener(layoutManager!!) {
                override var visibleThreshold: Int = 10

                override fun fetchNextPage() {
                    epoxyController.isLoading = true
                    viewModel.getNextPage(this@SearchResultFragment.nextUrl)
                }

                override fun isLoading(): Boolean {
                    return epoxyController.isLoading
                }
            }
            this.addOnScrollListener(loadMoreListener)
        }
    }

    companion object {
        private const val NEXT_URL = "NEXT_URL"
    }
}