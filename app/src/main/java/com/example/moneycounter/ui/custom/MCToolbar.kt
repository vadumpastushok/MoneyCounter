package com.example.moneycounter.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
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
    private var rightButtonClickListener: () -> Unit = {}

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
        binding.titleRightButton.setOnClickListener {
            rightButtonClickListener.invoke()
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
                val icon = getString(R.styleable.MCToolbar_right_btn_icon)
                val colorId = getResourceId(R.styleable.MCToolbar_icon_color, R.color.red)
                val color = context.getColor(colorId)
                val title = getString(R.styleable.MCToolbar_toolbar_title)
                val isRightButton = getBoolean(R.styleable.MCToolbar_is_right_button,false)



                setupLeftButton(color)
                title?.let{
                    setupTitleText(title)
                }
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

    fun setupTitleText (title : String){
        binding.titleText.text = title
    }

    private fun setupRightButton(icon : String? = null, color : Int? = null){
        icon?.let {
            binding.titleRightButton.setImageResource(
                context.resources.getIdentifier(
                    icon,
                    context.getString(R.string.drawable_folder),
                    context.packageName
                )
            )
        }

        color?.let {
            binding.titleRightButton.imageTintList =
                ColorStateList.valueOf(color)
        }
    }

    fun setupBottomLine(color: Int){
        binding.titleBottomLine.setBackgroundColor(color)
    }




}