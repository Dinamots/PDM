package fr.m1miage.tmdb.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(val layout: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {
    companion object {
        const val PAGE_SIZE = 8
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = layout.childCount
        val totalItemCount: Int = layout.itemCount

        if (!isLoading() && !isLastPage()) {
            if (!recyclerView.canScrollVertically(1)) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}