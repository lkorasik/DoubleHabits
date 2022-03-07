package com.lkorasik.doublehabits

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.color_picker.ColorPickerDialogBuilder
import com.lkorasik.doublehabits.databinding.ActivityAddHabitBinding


class HabitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddHabitBinding
    private lateinit var colorPickerDialog: ColorPickerDialogBuilder

    private var selectedColor: Int = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()
        initSpinnerAdapter()
        initColorPickerDialog()

        handleIntent()

        binding.chose.setOnClickListener {
            colorPickerDialog.show()
        }
    }

    private fun initActivity() {
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initSpinnerAdapter() {
        val source = R.array.priority_enum
        val view = android.R.layout.simple_spinner_item

        ArrayAdapter.createFromResource(this, source, view).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPriority.adapter = this
        }
    }

    private fun initColorPickerDialog() {
        colorPickerDialog = ColorPickerDialogBuilder(this, layoutInflater, binding.currentColor) { selectedColor = it }


        (binding.currentColor.background as GradientDrawable).apply {
            setColor(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
            colorPickerDialog.setSelected(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
        }
    }

    private fun handleIntent() {
        val habit = intent?.getParcelableExtra<Habit>(IntentKeys.Habit)

        if(habit != null){
            fillForm(habit)
            colorPickerDialog.setSelected(habit.color)
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

            when(habit.type) {
                HabitType.REGULAR -> radioGroup.check(R.id.type_regular)
                HabitType.HARMFUL -> radioGroup.check(R.id.type_harmful)
            }

            count.editText?.setText(habit.count.toString())
            periodicity.editText?.setText(habit.periodicity)
            (currentColor.background as GradientDrawable).apply {
                setColor(habit.color)
            }
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
