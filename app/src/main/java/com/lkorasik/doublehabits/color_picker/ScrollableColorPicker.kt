package com.lkorasik.doublehabits.color_picker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.lkorasik.doublehabits.R


//TODO: Попробуй переписать через адаптер для Horizontal scroll view
class ScrollableColorPicker : FrameLayout {
    private var onColorSelectedListener: OnColorSelected? = null

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle) {
        initView()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        initView()
    }

    constructor(ctx: Context) : super(ctx) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.widget_color_picker, this)

        val ids = arrayListOf(
            R.id.item0, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6,
            R.id.item7, R.id.item8, R.id.item9, R.id.item10, R.id.item11, R.id.item12, R.id.item13,
            R.id.item14, R.id.item15
        )

        setOnColorSelectedListeners(ids)

        findViewById<View>(R.id.view1).background = generateBackground()
        initSelectors(ids)
    }

    private fun setOnColorSelectedListeners(ids: List<Int>){
        for(i in ids.indices) {
            findViewById<View>(ids[i]).setOnClickListener {
                val hsv = floatArrayOf(getMiddle(i), 1f, 1f)
                val color = Color.HSVToColor(hsv)
                onColorSelectedListener?.onColorSelected(color)
            }
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

    private fun initSelectors(ids: List<Int>) {
        for(i in ids.indices) {
            val hsv = floatArrayOf(getMiddle(i), 1f, 1f)
            val color = Color.HSVToColor(hsv)

            (findViewById<View>(ids[i]).background as GradientDrawable).setColor(color)
        }
    }

    /**
     * Get the middle of the square.
     * 1) Get coordinates of square
     * 2) Get middle of square
     */
    private fun getMiddle(position: Int) = 360f / 16f * (position + 1) - 360f / 16f / 2f

    fun setOnColorSelectListener(listener: (color: Int) -> Unit) {
        onColorSelectedListener = OnColorSelected { color -> listener(color) }
    }
}
