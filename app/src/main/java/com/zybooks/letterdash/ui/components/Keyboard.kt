package com.zybooks.letterdash.ui.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.GridLayout
import com.zybooks.letterdash.R

class Keyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val keys = listOf(
        "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
        "A", "S", "D", "F", "G", "H", "J", "K", "L",
        "ENTER", "Z", "X", "C", "V", "B", "N", "M", "⌫"
    )

    private var listener: OnKeyboardActionListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.keyboard_layout, this, true)

        keys.forEach { key ->
            val button = findViewWithTag<Button>(key)
            button?.setOnClickListener {
                listener?.onKeyClicked(key)
            }

            button?.setBackgroundColor(Color.LTGRAY)
            button?.setTextColor(Color.BLACK)
        }

        layoutTransition = null // Disable layout transitions (CRUCIAL)

        post {
            val buttonWidth = context.resources.displayMetrics.widthPixels / 10 // Adjust as needed
            val buttonHeight = (buttonWidth * 1.2).toInt() // Adjust as needed

            keys.forEach { key ->
                val button = findViewWithTag<Button>(key)
                button?.layoutParams = LayoutParams(buttonWidth, buttonHeight) // Fixed button sizes
            }

            val desiredWidth = context.resources.displayMetrics.widthPixels
            val desiredHeight = (buttonHeight * 3) + paddingTop + paddingBottom // Fixed GridLayout height
            layoutParams = LayoutParams(desiredWidth, desiredHeight)
            requestLayout()
        }
    }

    fun setOnKeyboardActionListener(listener: OnKeyboardActionListener) {
        this.listener = listener
    }

    interface OnKeyboardActionListener {
        fun onKeyClicked(key: String)
    }

    fun updateKeyColor(key: String, color: Int) {
        val button = findViewWithTag<Button>(key)
        button?.setBackgroundColor(color)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        measureChildren()
        layoutChildren()
    }

    private fun measureChildren() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, 0, 0)
        }
    }

    private fun layoutChildren() {
        var top = paddingTop
        var left = paddingLeft
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val width = child.measuredWidth
            val height = child.measuredHeight

            child.layout(left, top, left + width, top + height)

            left += width
            if (left + width > getWidth() - paddingRight) {
                left = paddingLeft
                top += height
            }
        }
    }
}