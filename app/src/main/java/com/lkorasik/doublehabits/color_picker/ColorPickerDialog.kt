package com.lkorasik.doublehabits.color_picker

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.DialogColorPickerBinding
import java.math.RoundingMode

class ColorPickerDialog: DialogFragment() {
    private var _binding: DialogColorPickerBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var ctx: Context
    private var inited = false

    private var selected = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
    private var temp = selected

    private var colorSelectedListener: OnColorSelected? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        ctx = context
        _binding = DialogColorPickerBinding.inflate(LayoutInflater.from(ctx))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog
            .Builder(ctx)
            .setView(binding.root)
            .setTitle(ctx.getString(R.string.dialog_color_picker_title))
            .setPositiveButton(ctx.getString(R.string.dialog_color_picker_ok)) { dialog, _ -> positiveAction(dialog) }
            .setNegativeButton(ctx.getString(R.string.dialog_color_picker_cancel)) { dialog, _ -> negativeAction(dialog) }
            .create()

        inited = true

        setColorOnView()

        binding.colorPicker.setOnColorSelectListener {
            selected = it
            setColorOnView()
        }

        return dialog
    }

    private fun setColorOnView() {
        (binding.preview.background as GradientDrawable).setColor(selected)

        setRGB(selected)
        setHSV(selected)
    }

    private fun setRGB(color: Int) {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        binding.rgb.text = ctx.getString(R.string.dialog_color_picker_rgb).format(red, green, blue)
    }

    private fun setHSV(color: Int) {
        val hsvArray = floatArrayOf(0f, 0f, 0f)
        Color.colorToHSV(color, hsvArray)

        val hue = hsvArray[0].round()
        val saturation = hsvArray[1].round()
        val value = hsvArray[2].round()

        binding.hsv.text = ctx.getString(R.string.dialog_color_picker_hsv).format(hue, saturation, value)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setColorSelectedListener(listener: OnColorSelected) {
        colorSelectedListener = listener
    }

    private fun positiveAction(dialog: DialogInterface) {
        temp = selected
        colorSelectedListener?.onColorSelected(selected)

        dialog.dismiss()
    }

    private fun negativeAction(dialog: DialogInterface) {
        (binding.preview.background as GradientDrawable).setColor(temp)
        dialog.dismiss()
    }

    fun setColor(color: Int) {
        selected = color
        temp = color

        if(inited)
            setColorOnView()
    }

    private fun Float.round() = this.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
}
