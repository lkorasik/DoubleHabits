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

        handleIntent()

        initSpinnerAdapter()
    }

    private fun handleIntent() {
        intent?.apply {
            val habit = getParcelableExtra<Habit>(IntentKeys.Habit)

            habit?.let {
                binding.habitName.setText(it.name)
                binding.habitDescription.setText(it.description)
                binding.habitPriority.setSelection(it.priority.ordinal)

                if(it.type.ordinal == 0)
                    binding.radioGroup.check(R.id.first_type)
                else
                    binding.radioGroup.check(R.id.second_type)

                binding.count.setText(it.count.toString())
                binding.periodicity.setText(it.periodicity)
            }
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

    private fun initActivity() {
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        if(!isFormFilled()) {
            Snackbar
                .make(binding.root, getString(R.string.add_habit_empty_name), Snackbar.LENGTH_LONG)
                .show()
            return false
        }

        createNewHabit()
        return true
    }

    private fun createNewHabit() {
        getSelectedType()
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
            name = binding.habitName.text.toString(),
            description = binding.habitDescription.text.toString(),
            priority = getPriority(),
            type = getSelectedType(),
            periodicity = binding.periodicity.text.toString(),
            color = R.color.purple_500,
            count = getCount()
        )

    private fun getPriority(): Priority {
        val text = binding.habitPriority.selectedItem.toString()
        return Priority.valueOf(text.uppercase())
    }

    private fun getCount(): Int {
        if(binding.count.text.toString().isEmpty())
            return 0
        return binding.count.text.toString().toInt()
    }

    private fun getSelectedType(): HabitType {
        return when(binding.radioGroup.checkedRadioButtonId) {
            R.id.first_type -> HabitType.HARMFUL
            R.id.second_type -> HabitType.REGULAR
            else -> throw IllegalStateException("Incorrect habit type")
        }
    }

    private fun isFormFilled() = binding.habitName.text.toString().isNotEmpty()
}