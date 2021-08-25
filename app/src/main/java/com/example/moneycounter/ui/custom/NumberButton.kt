package com.example.moneycounter.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.moneycounter.R
import com.example.moneycounter.databinding.ElNumberButtonBinding


class NumberButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ElNumberButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.el_number_button,
            this,
            true
        )
        binding = ElNumberButtonBinding.bind(view)

        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.NumberButton,
            0,
            0
        ).apply {
            try {
                attrs?.let {
                    val isFingerPrintButton = getBoolean(R.styleable.NumberButton_is_fingerprint_button, false)
                    val isClearButton = getBoolean(R.styleable.NumberButton_is_clear_button, false)
                    val number = getInteger(R.styleable.NumberButton_number, 0)

                    when {
                        isFingerPrintButton -> {
                            setupFingerPrint()
                        }
                        isClearButton -> {
                            setupClearButton()
                        }
                        else -> {
                            setupText(number)
                        }
                    }
                }

            } finally {
                recycle()
            }
        }
    }

    fun setupFingerPrint(){
        binding.numberButtonImage.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.start_screen_icon_fingerprint, null))
    }

    fun disableFingerPrint(){
        binding.numberButtonImage.setImageDrawable(null)
    }

    fun setupClearButton(){
        binding.numberButtonImage.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.start_screen_icon_clear, null))
    }

    fun setupText(number: Int){
        binding.numberButton.text = number.toString()
    }

    fun getOrder(): String = binding.numberButton.text.toString()

}