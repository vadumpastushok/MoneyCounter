package com.example.moneycounter.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ElMcProgressbarBinding
import com.example.moneycounter.utils.ViewExtensions.dp


class MCProgressbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ElMcProgressbarBinding
    private var lines: MutableList<AppCompatImageView> = mutableListOf()

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.el_mc_progressbar,
            this,
            true
        )
        binding = ElMcProgressbarBinding.bind(view)

        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MCProgressbar,
            0,
            0
        ).apply {
            try {
                attrs?.let {
                    val linesNum = getInteger(R.styleable.MCProgressbar_lines_num, 0)
                    setLinesNum(linesNum)
                }
            } finally {
                recycle()
            }
        }
    }


    private fun setLinesNum(linesNum: Int){
        for(it in 0 until linesNum) {
            val line = AppCompatImageView(context)
            line.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            line.setPadding(6.dp)
            line.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.line_progressbar, null))
            lines.add(line)
           binding.mcProgressbarLayout.addView(line)
        }

    }

    fun setCompletedLinesNum(completedLinesNum: Int){
        for(i in 0 until lines.size){
            val line = lines[i]
            if(i < completedLinesNum) {
                line.imageTintList =
                    ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.gray, null))
            }else{
                line.imageTintList =
                    ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.dark_blue, null))
            }
        }
    }

    fun incorrectPassword(){
        for(it in lines){
            it.imageTintList =
                ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.red, null))
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.lines_horizontal_shake))
        }
    }

}