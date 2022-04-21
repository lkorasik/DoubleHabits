package com.lkorasik.doublehabits.ui.fragments

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.IntentKeys
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.FragmentViewHabitBinding
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType
import com.lkorasik.doublehabits.App
import com.lkorasik.doublehabits.ui.custom_views.color_picker.ColorPickerDialog
import com.lkorasik.doublehabits.view_model.EditorViewModel
import com.lkorasik.doublehabits.view_model.ViewModelFactory
import java.time.Instant


class HabitEditorFragment: Fragment() {
    private var fragmentViewHabitBinding: FragmentViewHabitBinding? = null
    private val binding
        get() = fragmentViewHabitBinding!!

    private lateinit var colorPickerDialog: ColorPickerDialog

    private val editorViewModel: EditorViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        hideKeyboard()
        findNavController().popBackStack()

        return true
    }

    private fun hideKeyboard() {
        val inputManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
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
        var habit = buildHabit()

        if(editorViewModel.getPosition() == null) {
            editorViewModel.addHabit(habit)
        }
        else{
            if(editorViewModel.getSelectedHabit().value != null)
                habit = buildHabit(editorViewModel.getSelectedHabit().value!!.id)
            editorViewModel.editHabit(habit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editorViewModel.getSelectedHabit().observe(viewLifecycleOwner) { habit ->
            habit?.let {
                fillForm(it)
            }
        }

        initSpinnerAdapter()
        initColorPickerDialog()

        handleArguments()

        binding.chose.setOnClickListener {
            colorPickerDialog.show(parentFragmentManager, "Dialog")
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
        colorPickerDialog = ColorPickerDialog()

        colorPickerDialog.setColorSelectedListener {
            editorViewModel.setSelectedColor(it)
            (binding.preview.background as GradientDrawable).setColor(it)
        }

        (binding.preview.background as GradientDrawable).apply {
            val color = Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f))
            setColor(color)
            colorPickerDialog.setColor(color)
        }
    }

    private fun handleArguments() {
        val habit = arguments?.getParcelable<Habit>(IntentKeys.Habit)
        arguments?.getInt(IntentKeys.Position)?.let { editorViewModel.setPosition(it) }

        if(editorViewModel.getPosition() != null)
            habit?.type?.let { editorViewModel.setHabitType(it) }

        if(habit != null) {
            editorViewModel.setSelectedColor(habit.color)
            colorPickerDialog.setColor(habit.color)
            setActivityTitleEdit()
        }
        else {
            clearForm()
            setActivityTitleCreate()
        }
    }

    private fun clearForm() {
        with(binding) {
            habitName.editText?.setText("")
            habitDescription.editText?.setText("")
            habitPriority.setSelection(HabitPriority.HIGH.ordinal)
            radioGroup.check(R.id.type_regular)
            count.editText?.setText("1")
            periodicity.editText?.setText("")
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

    private fun buildHabit(id: Long = 0): Habit {
        return Habit(
            id = id,
            name = binding.habitName.editText?.text.toString(),
            description = binding.habitDescription.editText?.text.toString(),
            priority = getPriority(),
            type = getSelectedType(),
            periodicity = binding.periodicity.editText?.text.toString(),
            color = editorViewModel.getSelectedColor(),
            count = getCount(),
            createdAt = editorViewModel.createdAt ?: Instant.now(),
            lastEditedAt = Instant.now()
        )
    }

    private fun getPriority(): HabitPriority {
        return HabitPriority.values()[binding.habitPriority.selectedItemPosition]
    }

    private fun getSelectedType(): HabitType {
        val selected = binding.radioGroup.checkedRadioButtonId
        return if(selected == R.id.type_regular) HabitType.REGULAR else HabitType.HARMFUL
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
}
