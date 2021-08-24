package com.example.moneycounter.features.category

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class CategoryTouchCallback(
    adapter: CategoryAdapter,
    val categoryMovedListener: () -> Unit
) :
    ItemTouchHelper.Callback() {

    private val mAdapter: CategoryAdapter = adapter
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        if(viewHolder.adapterPosition == mAdapter.getData().lastIndex) return 0
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = 0
        return makeMovementFlags(dragFlags, swipeFlags)
    }


    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        if (target.adapterPosition == mAdapter.getData().lastIndex) {
            return false
        }
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        categoryMovedListener.invoke()
    }



    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
    }

}