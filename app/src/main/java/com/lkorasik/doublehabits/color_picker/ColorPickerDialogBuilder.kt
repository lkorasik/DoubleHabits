package com.lkorasik.doublehabits.color_picker

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.DialogColorPickerBinding
import java.math.RoundingMode

//TODO: create new dialog and extends Dialog or AlertDialog, etc...
class ColorPickerDialogBuilder(private val context: Context) {
    private val binding = DialogColorPickerBinding.inflate(LayoutInflater.from(context))

    private val previewBackground = binding.preview.background as GradientDrawable

    private var selected = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
    private var temp = selected
    private val dialogBuilder = AlertDialog.Builder(context)
    private var dialog: AlertDialog

    private var colorSelectedListener: OnColorSelected? = null

    init {
        initDialogBuilder()

        binding.colorPicker.setOnColorSelectListener {
            selected = it
            setColorOnView()
        }

        dialog = dialogBuilder.create()
    }

    fun setColorSelectedListener(listener: OnColorSelected) {
        colorSelectedListener = listener
    }

    private fun initDialogBuilder() {
        with(dialogBuilder) {
            setView(binding.root)
            setTitle(context.getString(R.string.dialog_color_picker_title))

            setPositiveButton(context.getString(R.string.dialog_color_picker_ok)) { dialog, _ -> positiveAction(dialog) }
            setNegativeButton(context.getString(R.string.dialog_color_picker_cancel)) { dialog, _ -> negativeAction(dialog) }
        }

        setColorOnView()
    }

    private fun setColorOnView() {
        previewBackground.setColor(selected)

        setRGB(selected)
        setHSV(selected)
    }

    private fun positiveAction(dialog: DialogInterface) {
        temp = selected
        colorSelectedListener?.onColorSelected(selected)

        dialog.dismiss()
    }

    private fun negativeAction(dialog: DialogInterface) {
        previewBackground.setColor(temp)
        dialog.dismiss()
    }

    fun setColor(color: Int) {
        selected = color
        temp = color
        setColorOnView()
    }

    fun show() {
        dialog.show()
    }

    private fun setRGB(color: Int) {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        binding.rgb.text = context.getString(R.string.dialog_color_picker_rgb).format(red, green, blue)
    }

    private fun setHSV(color: Int) {
        val hsvArray = floatArrayOf(0f, 0f, 0f)
        Color.colorToHSV(color, hsvArray)

        val hue = hsvArray[0].round()
        val saturation = hsvArray[1].round()
        val value = hsvArray[2].round()

        binding.hsv.text = context.getString(R.string.dialog_color_picker_hsv).format(hue, saturation, value)
    }

    private fun Float.round() = this.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
}
