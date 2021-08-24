package com.example.moneycounter.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ElRoundTextButtonBinding


class RoundTextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ElRoundTextButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.el_round_text_button,
            this,
            true
        )
        binding = ElRoundTextButtonBinding.bind(view)

        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundTextButton,
            0,
            0
        ).apply {
            try {
                attrs?.let {
                    val icon = getString(R.styleable.RoundTextButton_btn_icon)
                    val colorId = getResourceId(R.styleable.RoundTextButton_btn_color, R.color.light_blue)
                    val color = context.getColor(colorId)
                    val title = getString(R.styleable.RoundTextButton_text_title)


                    icon?.let { setIcon(icon) }
                    setColor(color)
                    title?.let { setTitle(title)}
                }

            } finally {
                recycle()
            }
        }
    }
    fun setIcon(icon: String){
        if(context == null)return
        binding.ibIcon.setImageResource(context.resources.getIdentifier(icon, context.resources.getString(R.string.drawable_folder), context.packageName ))
    }
    fun setColor(color: Int){
        binding.ibIcon.backgroundTintList = ColorStateList.valueOf(color)
    }
    fun setTitle(title: String){
        binding.tvTitle.text = title
    }





}