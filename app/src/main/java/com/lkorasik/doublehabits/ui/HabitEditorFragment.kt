package com.lkorasik.doublehabits.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.*
import com.lkorasik.doublehabits.color_picker.ColorPickerDialogBuilder
import com.lkorasik.doublehabits.databinding.FragmentViewHabitBinding
import com.lkorasik.doublehabits.model.Habit

class HabitEditorFragment: Fragment() {
    private var fragmentViewHabitBinding: FragmentViewHabitBinding? = null
    private val binding
        get() = fragmentViewHabitBinding!!

    private lateinit var colorPickerDialog: ColorPickerDialogBuilder

    private var position: Int? = null
    private var selectedColor: Int = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentViewHabitBinding = FragmentViewHabitBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_habit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.create_habit -> handleSaveItemSelected()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleSaveItemSelected(): Boolean {
        if(!isValidInput())
            return false

        saveHabit()

        return true
    }

    private fun isValidInput(): Boolean {
        if(!isCorrectHabitName()) {
            showMessage(getString(R.string.add_habit_empty_name))
            return false
        }
        else if(!isCorrectCount()) {
            showMessage(getString(R.string.add_habit_incorrect_count))
            return false
        }

        return true
    }

    private fun isCorrectHabitName(): Boolean {
        return binding.habitName.editText?.text.toString().isNotBlank()
    }

    private fun isCorrectCount(): Boolean {
        if(binding.count.editText?.text.isNullOrEmpty())
            return false

        return binding.count.editText?.text.toString().toInt() > 0
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun saveHabit() {
        (activity as? MainActivity)?.apply {
            if(position == null)
                saveHabit(buildHabit())
            else
                saveHabit(buildHabit(), position!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerAdapter()
        initColorPickerDialog()

        handleIntent()

        binding.chose.setOnClickListener {
            colorPickerDialog.show()
        }
    }

    private fun initSpinnerAdapter() {
        val source = R.array.priority_enum
        val view = android.R.layout.simple_spinner_item

        ArrayAdapter.createFromResource(binding.root.context, source, view).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPriority.adapter = this
        }
    }

    private fun initColorPickerDialog() {
        colorPickerDialog = ColorPickerDialogBuilder(binding.root.context)
        colorPickerDialog.setColorSelectedListener {
            selectedColor = it
            (binding.preview.background as GradientDrawable).setColor(it)
        }

        (binding.preview.background as GradientDrawable).apply {
            val color = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
            setColor(color)
            colorPickerDialog.setColor(color)
        }
    }

    private fun handleIntent() {
        val habit = arguments?.getParcelable<Habit>(IntentKeys.Habit)
        position = arguments?.getInt(IntentKeys.Position)

        if(habit != null){
            fillForm(habit)
            selectedColor = habit.color
            colorPickerDialog.setColor(habit.color)
            setActivityTitleEdit()
        }
        else {
            setActivityTitleCreate()
        }
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
            (preview.background as GradientDrawable).apply {
                setColor(habit.color)
            }
        }
    }

    private fun setActivityTitleEdit() {
        (activity as AppCompatActivity).title = getString(R.string.add_habit_activity_edit_title)
    }

    private fun setActivityTitleCreate() {
        (activity as AppCompatActivity).title = getString(R.string.add_habit_activity_create_title)
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
            else -> throw IllegalStateException("Incorrect habit type") //TODO: подумай, может все таки стоит переписать
        }
    }

    private fun getCount(): Int {
        if(binding.count.editText?.text.toString().isEmpty())
            return 0
        return binding.count.editText?.text.toString().toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewHabitBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(): HabitEditorFragment {
            return HabitEditorFragment()
        }

        @JvmStatic
        fun newInstance(habit: Habit, position: Int): HabitEditorFragment {
            return HabitEditorFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IntentKeys.Habit, habit)
                    putInt(IntentKeys.Position, position)
                }
            }
        }
    }
}
