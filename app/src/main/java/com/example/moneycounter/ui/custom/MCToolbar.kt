package com.example.moneycounter.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
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

                val icon = getResourceId(R.styleable.MCToolbar_right_btn_icon, R.drawable.home_menu)
                val colorId: Int = getResourceId(R.styleable.MCToolbar_icon_color, R.color.red)
                val color = context.getColor(colorId)
                val title = getResourceId(R.styleable.MCToolbar_toolbar_title, R.string.category_title_add)
                val isRightButton = getBoolean(R.styleable.MCToolbar_is_right_button,false)


                setupLeftButton(color)
                setupTitleText(title)
                if(isRightButton){
                    setupRightButton(icon, color)
                }
                setupBottomLine(color)

            }finally {
                recycle()
            }
        }
    }

    fun setupLeftButton(color: Int){
        binding.titleLeftButton.imageTintList =
               ColorStateList.valueOf(color)
    }

    fun setupTitleText (@StringRes title : Int){
        binding.titleText.setText(title)
    }

    fun setupRightButton(@DrawableRes icon : Int){
        binding.titleRightButton.setImageResource(icon)
    }

    fun setupRightButton(@DrawableRes icon : Int, color : Int){
            binding.titleRightButton.imageTintList =
                ColorStateList.valueOf(color)
            binding.titleRightButton.setImageResource(icon)
    }

    fun setupBottomLine(color: Int){
        binding.titleBottomLine.setBackgroundColor(color)
    }




}