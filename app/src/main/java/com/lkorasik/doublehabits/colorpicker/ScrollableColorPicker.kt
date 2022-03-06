package com.lkorasik.doublehabits.colorpicker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.lkorasik.doublehabits.R


class ScrollableColorPicker : FrameLayout {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    private var onColorSelectedListener: OnColorSelected? = null

    fun setOnColorSelectListener(listener: OnColorSelected) {
        onColorSelectedListener = listener
    }

    fun setOnColorSelectListener(listener: (color: Int) -> Unit) {
        onColorSelectedListener = OnColorSelected { color -> listener(color) }
    }

    private fun initView() {
        inflate(context, R.layout.scrollable_color_picker, this)

        val ids = arrayListOf(
            R.id.item0, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6,
            R.id.item7, R.id.item8, R.id.item9, R.id.item10, R.id.item11, R.id.item12, R.id.item13,
            R.id.item14, R.id.item15
        )

        for(i in 0 until ids.size){
            findViewById<View>(ids[i]).setOnClickListener {
                val hsv = floatArrayOf(getMiddle(i), 1f, 1f)
                val color = Color.HSVToColor(hsv)
                onColorSelectedListener?.onColorSelected(color)
            }
        }

        findViewById<View>(R.id.view1).background = generateBackground()
        initSelectors(ids)
    }

    private fun getMiddle(i: Int) = 360f / 16f * (i + 1) - 360f / 16f / 2f

    private fun initSelectors(ids: List<Int>) {
        for(i in ids.indices) {
            val hsv = floatArrayOf(getMiddle(i), 1f, 1f)
            val color = Color.HSVToColor(hsv)

            val back = findViewById<View>(ids[i]).background as GradientDrawable
            back.setColor(color)
        }
    }

    private fun generateBackground(): GradientDrawable {
        val colors = (0..360 step 60)
            .map { it.toFloat() }
            .map { floatArrayOf(it, 1f, 1f) }
            .map { Color.HSVToColor(it) }
            .toIntArray()

        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
    }
}
