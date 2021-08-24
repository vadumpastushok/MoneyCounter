package com.example.moneycounter.features.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ItemCategoryBinding
import com.example.moneycounter.model.entity.db.Category
import java.util.*


class CategoryAdapter :
    RecyclerView.Adapter<CategoryViewHolder>(),
    CategoryTouchAdapter {

    private var data: MutableList<Category> = mutableListOf()
    private var categoryClickedListener: (position: Int) -> Unit = {}

    fun setCategoryClickedListener(listener: (order: Int) -> Unit){
        categoryClickedListener = listener
    }

    fun setData(newData: MutableList<Category>) {
        data = newData
        notifyDataSetChanged()
    }

    fun getData() = data

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }

        for(i in 0 until data.size-1)data[i].order = i


        notifyItemMoved(fromPosition, toPosition)
        return true
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_category,
                    parent,
                    false
                )
            )
        )
    }

    override fun onBindViewHolder(viewHolder: CategoryViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            categoryClickedListener.invoke(viewHolder.adapterPosition)
        }

        val binding = ItemCategoryBinding.bind(viewHolder.itemView)
        binding.rbtCategory.apply {
            setColor(data[position].color)
            setIcon(data[position].icon)
            setTitle(data[position].title)
        }
    }

    override fun getItemCount(): Int = data.size
}

class CategoryViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
