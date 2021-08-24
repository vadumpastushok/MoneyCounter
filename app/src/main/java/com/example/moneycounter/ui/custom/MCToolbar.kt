package com.example.moneycounter.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ElMcToolbarBinding

class MCToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr)  {

    private var binding: ElMcToolbarBinding
    private var backButtonClickListener: () -> Unit = {}

    fun setBackButtonClickListener(listener: () -> Unit) {
        backButtonClickListener = listener
    }

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.el_mc_toolbar,
            this,
            true
        )
        binding = ElMcToolbarBinding.bind(view)
        binding.titleLeftButton.setOnClickListener {
            backButtonClickListener.invoke()
        }
        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MCToolbar,
            0,
            0
        ).apply {
            try {

                val color = getResourceId(R.styleable.MCToolbar_icon_color, R.color.light_blue)
                binding.titleLeftButton.imageTintList =
                    ContextCompat.getColorStateList(context, color)
                binding.titleBottomLine.setBackgroundResource(color)

                val title = getResourceId(R.styleable.MCToolbar_toolbar_title, R.string.category_title_add)
                binding.titleText.setText(title)


                val isRightButton = getBoolean(R.styleable.MCToolbar_is_right_button,false)
                val icon = getResourceId(R.styleable.MCToolbar_right_btn_icon, R.drawable.home_menu)
                if(isRightButton)setupRightButton(icon, color)

            }finally {
                recycle()
            }
        }
    }

    fun setupLeftButton(@ColorRes color: Int){
        binding.titleLeftButton.imageTintList =
            ContextCompat.getColorStateList(context, color)
    }

    fun setupTitleText (@StringRes title : Int){
        binding.titleText.setText(title)
    }

    fun setupRightButton(@DrawableRes icon : Int, @ColorRes color : Int){
            binding.titleRightButton.imageTintList =
                ContextCompat.getColorStateList(context, color)
            binding.titleRightButton.setImageResource(icon)
    }




}