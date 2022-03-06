package com.lkorasik.doublehabits

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.colorpicker.ScrollableColorPicker
import com.lkorasik.doublehabits.databinding.ActivityAddHabitBinding
import java.math.RoundingMode


class AddHabitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddHabitBinding
    private lateinit var colorPickerDialog: AlertDialog

    private var selectedColor: Int = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()
        initSpinnerAdapter()
        initColorPickerDialog()

        handleIntent()

        (binding.currentColor.background as GradientDrawable).apply {
            setColor(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
        }

        binding.chose.setOnClickListener {
            colorPickerDialog.show()
        }
    }

    private fun initColorPickerDialog() {
        colorPickerDialog = AlertDialog.Builder(this).let {
            val view = layoutInflater.inflate(R.layout.dialog_color_picker, null)
            it.setView(view)

            it.setTitle("Select color")

            var selected = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
            val back = view.findViewById<View>(R.id.current_color).background as GradientDrawable
            back.setColor(selected)
            var temp = selected

            val rgb = view.findViewById<TextView>(R.id.rgb)
            val hsv = view.findViewById<TextView>(R.id.hsv)

            val picker = view.findViewById<ScrollableColorPicker>(R.id.scrollable_color_picker)
            picker.setOnColorSelectListener {
                back.setColor(it)

                val red = Color.red(it)
                val green = Color.green(it)
                val blue = Color.blue(it)
                rgb.text = getString(R.string.dialog_color_picker_rgb).format(red, green, blue)

                val hsvArray = floatArrayOf(0f, 0f, 0f)
                Color.colorToHSV(it, hsvArray)
                val hue = hsvArray[0].toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                val saturation = hsvArray[1].toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                val value = hsvArray[2].toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                hsv.text = getString(R.string.dialog_color_picker_hsv).format(hue, saturation, value)

                selected = it
            }

            it.setPositiveButton("Ok") { dialog, which ->
                (binding.currentColor.background as GradientDrawable).apply {
                    setColor(selected)
                }
                temp = selected
                selectedColor = selected
                dialog.dismiss()
            }

            it.setNegativeButton("Cancel") { dialog, which ->
                selected = selectedColor
                back.setColor(temp)
            }

            it.create()
        }
    }

    private fun initActivity() {
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleIntent() {
        val habit = intent?.getParcelableExtra<Habit>(IntentKeys.Habit)

        if(habit != null){
            fillForm(habit)
            setActivityTitleEdit()
        }
        else {
            setActivityTitleCreate()
        }
    }

    private fun setActivityTitleEdit() {
        title = getString(R.string.add_habit_activity_edit_title)
    }

    private fun setActivityTitleCreate() {
        title = getString(R.string.add_habit_activity_create_title)
    }

    private fun fillForm(habit: Habit) {
        with(binding) {
            habitName.editText?.setText(habit.name)
            habitDescription.editText?.setText(habit.description)
            habitPriority.setSelection(habit.priority.ordinal)

            when(habit.type){
                HabitType.REGULAR -> radioGroup.check(R.id.type_regular)
                HabitType.HARMFUL -> radioGroup.check(R.id.type_harmful)
            }

            count.editText?.setText(habit.count.toString())
            periodicity.editText?.setText(habit.periodicity)
        }
    }

    private fun initSpinnerAdapter() {
        val source = R.array.priority_enum
        val view = android.R.layout.simple_spinner_item
        ArrayAdapter.createFromResource(this, source, view).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPriority.adapter = this
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_habit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.create_habit -> handleCreateHabitSelection()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleCreateHabitSelection(): Boolean {
        if(!isMinimalFormFilled()) {
            Snackbar
                .make(binding.root, getString(R.string.add_habit_empty_name), Snackbar.LENGTH_LONG)
                .show()
            return false
        }

        createNewHabit()
        return true
    }

    private fun isMinimalFormFilled() = binding.habitName.editText?.text.toString().isNotEmpty()

    private fun createNewHabit() {
        val habit = buildHabit()
        val newIntent = Intent().apply {
            putExtra(IntentKeys.Habit, habit)

            val position = intent.getIntExtra(IntentKeys.Position, -1)
            if(position != -1)
                putExtra(IntentKeys.Position, position)
        }
        setResult(Activity.RESULT_OK, newIntent)
        finish()
    }

    private fun buildHabit() =
        Habit(
            name = binding.habitName.editText?.text.toString(),
            description = binding.habitDescription.editText?.text.toString(),
            priority = getPriority(),
            type = getSelectedType(),
            periodicity = binding.periodicity.editText?.text.toString(),
//            color = binding.currentColor.solidColor,
            color = selectedColor,
            count = getCount()
        )

    private fun getPriority(): Priority {
        return Priority.values()[binding.habitPriority.selectedItemPosition]
    }

    private fun getSelectedType(): HabitType {
        return when(binding.radioGroup.checkedRadioButtonId) {
            R.id.type_harmful -> HabitType.HARMFUL
            R.id.type_regular -> HabitType.REGULAR
            else -> throw IllegalStateException("Incorrect habit type")
        }
    }

    private fun getCount(): Int {
        if(binding.count.editText?.text.toString().isEmpty())
            return 0
        return binding.count.editText?.text.toString().toInt()
    }
}
