package com.lkorasik.doublehabits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lkorasik.doublehabits.BuildConfig
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.FragmentAboutBinding

class AboutFragment: Fragment() {
    private var fragmentAboutBinding: FragmentAboutBinding? = null
    private val binding
        get() = fragmentAboutBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.version.text = getString(R.string.about_version).format(BuildConfig.VERSION_NAME)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentAboutBinding = null
    }
}