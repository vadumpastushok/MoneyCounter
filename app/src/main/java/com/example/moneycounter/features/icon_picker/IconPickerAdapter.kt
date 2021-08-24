package com.example.moneycounter.features.icon_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ItemIconPickerBinding

class IconPickerAdapter: RecyclerView.Adapter<IconPickerAdapter.IconViewHolder>() {

    private var iconClickedListener: (icon: String) -> Unit = {}
    private var data: MutableList<String> = mutableListOf()
    private var color: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return IconViewHolder(ItemIconPickerBinding.bind(LayoutInflater.from(parent.context).inflate(
            R.layout.item_icon_picker, parent, false
        )))
    }


    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val binding = ItemIconPickerBinding.bind(holder.itemView)
        binding.btnIconPicker.setIcon(data[position])
        binding.btnIconPicker.setColor(color)
        binding.btnIconPicker.setOnClickListener {
            iconClickedListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: MutableList<String>, newColor: Int){
        data = newData
        color = newColor
        notifyDataSetChanged()
    }


    fun setCategoryClickedListener(listener: (icon: String) -> Unit) {
        iconClickedListener = listener
    }



    class IconViewHolder(val binding: ItemIconPickerBinding) : RecyclerView.ViewHolder(binding.root)
}