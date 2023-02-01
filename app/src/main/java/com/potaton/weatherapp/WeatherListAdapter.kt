package com.potaton.weatherapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potaton.weatherapp.tab1.Tab1Fragment
import com.potaton.weatherapp.tab2.Tab2Fragment
import com.potaton.weatherapp.tab3.Tab3Fragment
import com.potaton.weatherapp.tab4.Tab4Fragment
import com.potaton.weatherapp.tab5.Tab5Fragment
import com.potaton.weatherapp.tab6.Tab6Fragment
import com.potaton.weatherapp.tab7.Tab7Fragment

// 1週間分のフラグメント 0~6

private const val NUM_PAGES = 7

class WeatherListAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Tab1Fragment()
            1 -> return Tab2Fragment()
            2 -> return Tab3Fragment()
            3 -> return Tab4Fragment()
            4 -> return Tab5Fragment()
            5 -> return Tab6Fragment()
            6 -> return Tab7Fragment()
        }
        return Tab1Fragment()
    }
}