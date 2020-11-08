package comtest.ct.cd.zulfikar.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * This class is for implementing the load more behavior in recycler view. It will trigger
 * [onLoadMore] method when it reach the bottom of thelist + your visible item [visibleThreshold]
 */

abstract class EndlessRecyclerViewScrollListener(private var visibleThreshold: Int = 5): RecyclerView.OnScrollListener() {
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    constructor(visibleThreshold: Int, layoutManager: LinearLayoutManager): this(visibleThreshold) {
        this.visibleThreshold = visibleThreshold
        this.mLayoutManager = layoutManager
    }

    constructor(visibleThreshold: Int, layoutManager: GridLayoutManager): this(visibleThreshold) {
        this.mLayoutManager = layoutManager
        this.visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(visibleThreshold: Int, layoutManager: StaggeredGridLayoutManager): this(visibleThreshold) {
        this.mLayoutManager = layoutManager
        this.visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (index in lastVisibleItemPositions.indices) {
            if (index == 0) {
                maxSize = lastVisibleItemPositions[index]
            } else if (lastVisibleItemPositions[index] > maxSize) {
                maxSize = lastVisibleItemPositions[index]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager?.itemCount ?: 0

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}
