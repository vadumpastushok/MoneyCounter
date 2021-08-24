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
        return isMovementEnabled
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = 0
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder?.itemView != null) {
            mAdapter.onItemSelected(viewHolder.itemView)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        categoryMovedListener.invoke()
        mAdapter.onItemClear(viewHolder.itemView)
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
    }

    companion object{
        private var isMovementEnabled: Boolean = false
        fun setIsMovementEnabled(isEnabled: Boolean){
            isMovementEnabled = isEnabled
        }
    }
}