package com.kevin1008.widget.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreListener(private val layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun fetchNextPage()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == 0 && dy == 0) {
            return
        }
        if (couldLoadMore()) {
            fetchNextPage()
        }
    }

    /**
     * Item count prior you need to trigger load more
     */
    protected open var visibleThreshold: Int = 5

    /**
     * Could implement count prior to bottom you want to trigger load more while you scroll
     */
    protected open fun onLoadMore(): Boolean {
        return getLastVisibleItemPosition() + visibleThreshold >= (layoutManager.itemCount - 1)
    }

    /**
     * Let outside to determine is loading or not
     */
    protected open fun isLoading(): Boolean {
        return false
    }

    /**
     * return mechanism is loading or not
     */
    protected fun couldLoadMore(): Boolean {
        return onLoadMore() && !isLoading()
    }

    private fun getLastVisibleItemPosition(): Int {
        return when (layoutManager) {
            is LinearLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            else -> {
                //Could extend more layout manager
                0
            }
        }
    }
}