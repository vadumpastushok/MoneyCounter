package com.example.moneycounter.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.R
import com.example.moneycounter.utils.ViewExtensions.dp


class DividerItemDecoration(
    private val context: Context,
    private val paddingLeft: Int,
    private val skipAtTop: Int = 0,
    private val skipAtBottom: Int = 0,
    private val filterById: Int? = null
): RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = ContextCompat.getDrawable(context, R.drawable.divider) ?: return
        val left = parent.paddingLeft+paddingLeft.dp
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in skipAtTop until (childCount - skipAtBottom)) {
            val child: View = parent.getChildAt(i)
            if(filterById == child.id) continue

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}