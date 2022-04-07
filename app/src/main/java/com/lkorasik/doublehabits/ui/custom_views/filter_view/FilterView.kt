package com.lkorasik.doublehabits.ui.custom_views.filter_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import com.lkorasik.doublehabits.sort.HabitSort
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.WidgetFilterBinding
import com.lkorasik.doublehabits.sort.SortComparatorFactory
import com.lkorasik.doublehabits.sort.SortDirection

class FilterView: LinearLayout {
    private lateinit var binding: WidgetFilterBinding

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
        binding = WidgetFilterBinding.inflate(LayoutInflater.from(context), this, true)

        val source = R.array.sorting_types_enum
        val view = android.R.layout.simple_spinner_item

        ArrayAdapter.createFromResource(context, source, view).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.selectFilter.adapter = this
        }
    }

    fun setOnAcceptListener(listener: OnAcceptListener) {
        binding.applyFilters.setOnClickListener {
            val sortType = HabitSort.values()[binding.selectFilter.selectedItemPosition]
            val selected = binding.radioGroup.checkedRadioButtonId
            val sortDirection = if(selected == R.id.type_ascending) SortDirection.ASCENDING else SortDirection.DESCENDING
            val checked = binding.caseFlag.isChecked

            val comparator = SortComparatorFactory.build(sortType, sortDirection)

            listener.onAcceptListener(binding.searchText.text.toString(), comparator, checked)
        }
    }
}
