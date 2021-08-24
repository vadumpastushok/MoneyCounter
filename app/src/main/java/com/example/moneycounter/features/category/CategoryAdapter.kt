package com.example.moneycounter.features.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ItemCategoryBinding
import com.example.moneycounter.model.entity.Category


class CategoryAdapter() : RecyclerView.Adapter<CategoryViewHolder>() {

    private var data: MutableList<Category> = mutableListOf()
    private var categoryListener: (position: Int) -> Unit = {}

    fun setCategoryListener(listener: (position: Int) -> Unit){
        categoryListener = listener
    }

    fun setData(newData: MutableList<Category>) {
        data = newData
        notifyDataSetChanged()
    }

    fun getData() = data

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
            categoryListener.invoke(position)
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