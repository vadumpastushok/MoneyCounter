package com.example.moneycounter.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.moneycounter.model.entity.ui.Analytics


class RecycleDiffUtilCallback(oldList: List<Analytics>, newList: List<Analytics>) :
    DiffUtil.Callback() {
    private val oldList: List<Analytics> = oldList
    private val newList: List<Analytics> = newList

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Analytics = oldList[oldItemPosition]
        val newItem: Analytics = newList[newItemPosition]
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Analytics = oldList[oldItemPosition]
        val newItem: Analytics = newList[newItemPosition]
        return (oldItem.title == newItem.title
                && oldItem.amount == newItem.amount
                && oldItem.icon == newItem.icon
                && oldItem.icon_color == newItem.icon_color)
    }

}