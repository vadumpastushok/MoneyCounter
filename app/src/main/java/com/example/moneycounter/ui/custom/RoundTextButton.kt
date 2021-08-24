package com.example.moneycounter.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
                    val icon = getResourceId(
                        R.styleable.RoundTextButton_btn_icon,
                        R.drawable.home_icon_costs
                    )
                    val color = getResourceId(R.styleable.RoundTextButton_btn_color, R.color.light_blue)
                    val title = getString(R.styleable.RoundTextButton_text_title)

                    binding.ibIcon.setImageResource(icon)
                    binding.ibIcon.backgroundTintList =
                        ContextCompat.getColorStateList(context, color)
                    title?.let { binding.tvTitle.text = title }
                }

            } finally {
                recycle()
            }
        }
    }
    fun setIcon(icon: Int){
        binding.ibIcon.setImageResource(icon)
    }
    fun setColor(color: Int){
        binding.ibIcon.backgroundTintList= ContextCompat.getColorStateList(context, color)
    }
    fun setTitle(title: Int){
        binding.tvTitle.text=resources.getString(title)
    }


}