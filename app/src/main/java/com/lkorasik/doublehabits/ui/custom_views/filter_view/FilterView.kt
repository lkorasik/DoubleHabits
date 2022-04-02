package com.lkorasik.doublehabits.ui.custom_views.filter_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import com.lkorasik.doublehabits.sort.HabitSort
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.BottomSheetBinding
import com.lkorasik.doublehabits.sort.SortComparatorFactory
import com.lkorasik.doublehabits.sort.SortDirection

class FilterView: LinearLayout {
    private lateinit var button: Button
    private lateinit var searchView: EditText
    private lateinit var spinner: Spinner
    private lateinit var sortDirectionSelector: RadioGroup
    private lateinit var ignoreLetterCase: CheckBox

    private lateinit var binding: BottomSheetBinding

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle) {
        initView()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        initView()
    }

    constructor(ctx: Context) : super(ctx) {
        initView()
    }

    private fun initView() {
        binding = BottomSheetBinding.inflate(LayoutInflater.from(context), this, true)
//        inflate(context, R.layout.bottom_sheet, this)

        spinner = binding.selectFilter
//        spinner = findViewById(R.id.select_filter)
        val source = R.array.sorting_types_enum
        val view = android.R.layout.simple_spinner_item

        ArrayAdapter.createFromResource(context, source, view).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = this
        }

//        button = findViewById(R.id.apply_filters)
        button = binding.applyFilters
//        searchView = findViewById(R.id.search_text)
        searchView = binding.searchText
//        sortDirectionSelector = findViewById(R.id.radio_group)
        sortDirectionSelector = binding.radioGroup
//        ignoreLetterCase = findViewById(R.id.case_flag)
        ignoreLetterCase = binding.caseFlag
    }

    fun setOnAcceptListener(listener: OnAcceptListener) {
        button.setOnClickListener {
            val sortType = HabitSort.values()[spinner.selectedItemPosition]
            val selected = sortDirectionSelector.checkedRadioButtonId
            val sortDirection = if(selected == R.id.type_ascending) SortDirection.ASCENDING else SortDirection.DESCENDING
            val checked = ignoreLetterCase.isChecked

            val comparator = SortComparatorFactory.build(sortType, sortDirection)

            listener.onAcceptListener(searchView.text.toString(), comparator, checked)
        }
    }
}
