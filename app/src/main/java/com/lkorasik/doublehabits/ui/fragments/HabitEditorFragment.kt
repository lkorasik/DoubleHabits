package com.lkorasik.doublehabits.ui.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.IntentKeys
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.FragmentViewHabitBinding
import com.lkorasik.doublehabits.ui.custom_views.color_picker.ColorPickerDialog
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType
import com.lkorasik.doublehabits.view_model.EditorViewModel
import java.time.Instant

class HabitEditorFragment: Fragment() {
    private var fragmentViewHabitBinding: FragmentViewHabitBinding? = null
    private val binding
        get() = fragmentViewHabitBinding!!

    private lateinit var colorPickerDialog: ColorPickerDialog

    private val editorViewModel: EditorViewModel by activityViewModels()

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
        findNavController().popBackStack()

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
        val habit = buildHabit()

        if(editorViewModel.getPosition() == null) {
            editorViewModel.addHabit(habit)
        }
        else {
            if((editorViewModel.getType() != null) && (habit.type != editorViewModel.getType()))
                editorViewModel.moveHabit(habit)
            else
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
        //TODO: Пойми, тебе нужны аргументы сейчас? В таком ли виде
        val habit = arguments?.getParcelable<Habit>(IntentKeys.Habit)
        arguments?.getInt(IntentKeys.Position)?.let { editorViewModel.setPosition(it) }

        if(editorViewModel.getPosition() != null)
            habit?.type?.let { editorViewModel.setHabitType(it) }

        if(habit != null) {
            editorViewModel.createdAt = habit.createdAt
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

    //TODO: при возаращении на экран спичка привычек убирать клаву
    private fun buildHabit(): Habit {
        return Habit(
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

    //TODO: Попробуй сделать передачу данных через SafeArgs
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