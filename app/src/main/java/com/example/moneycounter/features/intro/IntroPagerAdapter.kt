package com.example.moneycounter.features.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.databinding.PolicyItemIntroBinding
import com.example.moneycounter.databinding.StandardItemIntroBinding
import com.example.moneycounter.model.entity.Intro
import com.example.moneycounter.model.entity.IntroType


class IntroPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<Intro> = mutableListOf()
    var radioButtonListener: (() -> Unit)? = null

    fun setData(newData: MutableList<Intro>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            IntroType.STANDARD.id -> StandardVH(
                StandardItemIntroBinding.bind(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.standard_item_intro,
                        parent,
                        false
                    )
                )
            )
            else -> PolicyVH(
                PolicyItemIntroBinding.bind(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.policy_item_intro,
                        parent,
                        false
                    )
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            IntroType.STANDARD.id -> {
                val standard = holder as StandardVH
                bindStandard(standard.binding, position)
            }
            IntroType.POLICY.id -> {
                val policy = holder as PolicyVH
                bindPolicy(policy.binding)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].introType.id
    }

    private fun bindStandard(binding: StandardItemIntroBinding, position: Int) {

        binding.tvIntroTitle.setText(data[position].title)
        binding.tvIntroText.setText(data[position].text)
        binding.imageIntroItem.setImageResource(data[position].image)
    }
    private fun bindPolicy(binding: PolicyItemIntroBinding) {
        binding.radioIntroPolicy.setOnClickListener {
            radioButtonListener?.invoke()
        }
    }

    class StandardVH(val binding: StandardItemIntroBinding) : RecyclerView.ViewHolder(binding.root)
    class PolicyVH(val binding: PolicyItemIntroBinding) : RecyclerView.ViewHolder(binding.root)
}


