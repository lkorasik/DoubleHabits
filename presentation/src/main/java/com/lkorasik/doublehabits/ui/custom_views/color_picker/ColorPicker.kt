package com.lkorasik.doublehabits.ui.custom_views.color_picker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.WidgetColorPickerBinding


//TODO: Попробуй переписать через адаптер для Horizontal scroll view
class ColorPicker : FrameLayout {
    private lateinit var binding: WidgetColorPickerBinding
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
        binding = WidgetColorPickerBinding
            .inflate(LayoutInflater.from(context), this, true)

        val ids = arrayListOf(
            binding.item0, binding.item1, binding.item2, binding.item3, binding.item4,
            binding.item5, binding.item6, binding.item7, binding.item8, binding.item9,
            binding.item10, binding.item11, binding.item12, binding.item13, binding.item14,
            binding.item15)

        setOnColorSelectedListeners(ids)

        findViewById<View>(R.id.view1).background = generateBackground()
        initSelectors(ids)
    }

    private fun setOnColorSelectedListeners(ids: List<View>){
        for(i in ids.indices) {
            ids[i].setOnClickListener {
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

    private fun initSelectors(ids: List<View>) {
        for(i in ids.indices) {
            val hsv = floatArrayOf(getMiddle(i), 1f, 1f)
            val color = Color.HSVToColor(hsv)

            (ids[i].background as GradientDrawable).setColor(color)
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
