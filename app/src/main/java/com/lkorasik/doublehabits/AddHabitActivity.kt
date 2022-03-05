package com.lkorasik.doublehabits

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.databinding.ActivityAddHabitBinding

class AddHabitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initSpinnerAdapter()
        handleIntent()
    }

    private fun initActivity() {
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleIntent() {
        intent?.getParcelableExtra<Habit>(IntentKeys.Habit)?.let {
            fillForm(it)
        }
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
            color = R.color.purple_500,
            count = getCount()
        )

    private fun getPriority(): Priority {
        val text = binding.habitPriority.selectedItem.toString()
        return Priority.valueOf(text.uppercase())
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