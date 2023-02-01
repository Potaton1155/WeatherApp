package com.potaton.weatherapp.tab3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.potaton.weatherapp.R
import com.potaton.weatherapp.databinding.FragmentTab1Binding
import com.potaton.weatherapp.databinding.FragmentTab3Binding

class Tab3Fragment : Fragment(R.layout.fragment_tab3) {
    private var _binding: FragmentTab3Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTab3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}