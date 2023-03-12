package com.potaton.weatherapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potaton.weatherapp.tab1.Tab1Fragment
import com.potaton.weatherapp.tab2.Tab2Fragment
import com.potaton.weatherapp.tab3.Tab3Fragment
import com.potaton.weatherapp.tab4.Tab4Fragment
import com.potaton.weatherapp.tab5.Tab5Fragment

class WeatherListAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        Tab1Fragment(),
        Tab2Fragment(),
        Tab3Fragment(),
        Tab4Fragment(),
        Tab5Fragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}