package com.example.moneycounter.features.financial_place

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.databinding.ItemFinancialPlaceBinding
import com.example.moneycounter.model.entity.db.FinancialPlace
import java.util.*


class FinancialPlaceAdapter: RecyclerView.Adapter<FinancialPlaceViewHolder>(), FinancialPlaceTouchAdapter {

    private var data: MutableList<FinancialPlace> = mutableListOf()
    private var categoryClickedListener: (position: Int) -> Unit = {}
    private var deleteCategoryClickedListener: (position: Int) -> Unit = {}
    private var isEditable: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialPlaceViewHolder {
        return FinancialPlaceViewHolder(
            ItemFinancialPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

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

        for (i in 0 until data.size - 1) data[i].order = i

        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onBindViewHolder(viewHolder: FinancialPlaceViewHolder, position: Int) {
        viewHolder.binding.rbtFinancialPlace.apply {
            setColor(data[position].color)
            setIcon(data[position].icon)
            setTitle(data[position].title)
            setOnClickListener {
                categoryClickedListener.invoke(position)
            }
        }

        viewHolder.binding.ivFinancialPlaceClose.isVisible = isEditable
        viewHolder.binding.ivFinancialPlaceClose.setOnClickListener {
            deleteCategoryClickedListener.invoke(viewHolder.adapterPosition)
        }

        animEdit(viewHolder.itemView)
    }

    override fun onViewAttachedToWindow(holder: FinancialPlaceViewHolder) {
        super.onViewAttachedToWindow(holder)
        animEdit(holder.itemView)
    }

    private fun animEdit(itemView: View) {
        if(isEditable){
            itemView.startAnimation(AnimationUtils.loadAnimation(App.context, R.anim.shake))
        }else{
            itemView.clearAnimation()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIsEditable(editable: Boolean) {
        isEditable = editable
        notifyDataSetChanged()
    }

    fun setCategoryClickedListener(listener: (order: Int) -> Unit) {
        categoryClickedListener = listener
    }

    fun setDeleteCategoryClickedListener(listener: (order: Int) -> Unit) {
        deleteCategoryClickedListener = listener
    }

    private fun animSelectedView(view: View, fromScale: Float, toScale: Float) {
        ValueAnimator.ofFloat(fromScale, toScale).apply {
            duration = 250
            addUpdateListener {
                view.scaleX = it.animatedValue as Float
                view.scaleY = it.animatedValue as Float
            }
            start()
        }
    }

    override fun onItemSelected(itemView: View) {
        animSelectedView(itemView, 1f, 1.2f)
        vibrate()
    }

    private fun vibrate(){
        val vibrator = App.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override fun onItemClear(itemView: View) {
        animSelectedView(itemView, 1.2f, 1f)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: MutableList<FinancialPlace>) {
        data = newData
        notifyDataSetChanged()
    }

    fun getData() = data
}

class FinancialPlaceViewHolder(val binding: ItemFinancialPlaceBinding) : RecyclerView.ViewHolder(binding.root)
