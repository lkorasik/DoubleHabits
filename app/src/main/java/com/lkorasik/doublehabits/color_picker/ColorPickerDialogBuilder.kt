package com.lkorasik.doublehabits.color_picker

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.lkorasik.doublehabits.R
import java.math.RoundingMode

class ColorPickerDialogBuilder(context: Context, layoutInflater: LayoutInflater, private val currentColor: View, private val callback: (Int) -> Unit) {
    private val view = layoutInflater.inflate(R.layout.dialog_color_picker, null)

    private val previewBackground = view.findViewById<View>(R.id.current_color).background as GradientDrawable
    private val rgb = view.findViewById<TextView>(R.id.rgb)
    private val hsv = view.findViewById<TextView>(R.id.hsv)
    private val picker = view.findViewById<ScrollableColorPicker>(R.id.scrollable_color_picker)

    private val title = context.getString(R.string.dialog_color_picker_title)
    private val rgbPattern = context.getString(R.string.dialog_color_picker_rgb)
    private val hsvPattern = context.getString(R.string.dialog_color_picker_hsv)
    private val ok = context.getString(R.string.dialog_color_picker_ok)
    private val cancel = context.getString(R.string.dialog_color_picker_cancel)

    private var selected = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
    private var temp = selected
    private val dialogBuilder = AlertDialog.Builder(context)
    private var dialog: AlertDialog

    init {
        initDialogBuilder()

        picker.setOnColorSelectListener {
            selected = it
            setSelectedColor()
        }

        dialog = dialogBuilder.create()
    }

    private fun positiveAction(dialog: DialogInterface) {
        (currentColor.background as GradientDrawable).setColor(selected)

        temp = selected
        callback(selected)

        dialog.dismiss()
    }

    private fun negativeAction(dialog: DialogInterface) {
        previewBackground.setColor(temp)
        dialog.dismiss()
    }

    private fun initDialogBuilder() {
        with(dialogBuilder) {
            setView(view)
            setTitle(title)

            setPositiveButton(ok) { dialog, _ -> positiveAction(dialog) }
            setNegativeButton(cancel) { dialog, _ -> negativeAction(dialog) }
        }

        setSelectedColor()
    }

    fun setSelected(color: Int) {
        selected = color
        setSelectedColor()
    }

    fun show() {
        dialog.show()
    }

    private fun setSelectedColor() {
        previewBackground.setColor(selected)

        setRGB(selected)
        setHSV(selected)
    }

    private fun setRGB(color: Int) {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        rgb.text = rgbPattern.format(red, green, blue)
    }

    private fun setHSV(color: Int) {
        val hsvArray = floatArrayOf(0f, 0f, 0f)
        Color.colorToHSV(color, hsvArray)

        val hue = hsvArray[0].round()
        val saturation = hsvArray[1].round()
        val value = hsvArray[2].round()

        hsv.text = hsvPattern.format(hue, saturation, value)
    }

    private fun Float.round() = this.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
}
